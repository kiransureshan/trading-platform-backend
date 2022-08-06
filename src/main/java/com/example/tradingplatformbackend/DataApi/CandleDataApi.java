package com.example.tradingplatformbackend.DataApi;

import com.example.tradingplatformbackend.Models.CandleBarData;
import com.example.tradingplatformbackend.Models.CandleTimeFrame;
import com.example.tradingplatformbackend.Models.StreamData;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.Bar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.realtime.enums.MarketDataMessageType;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.crypto.common.enums.Exchange;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.crypto.historical.bar.CryptoBar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.crypto.historical.bar.CryptoBarsResponse;
import net.jacobpeterson.alpaca.websocket.marketdata.MarketDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CandleDataApi implements Runnable{
    private final AlpacaAPI api;
    private final HashSet<String> subscribedTickers;
	private String primaryStream;
	private final SimpMessageSendingOperations so;
	private int quoteThrottle;

	private int numBarsHistory;

    public CandleDataApi(SimpMessageSendingOperations so, String primaryTicker){
        this.api = new AlpacaAPI();
		this.so = so;
		this.primaryStream = primaryTicker;
		this.subscribedTickers = new HashSet<>();
		this.quoteThrottle = 0;
		subscribedTickers.add(primaryStream);
		this.numBarsHistory = 50;
    }

	@Override
	public void run() {
		try{
			// tell listener what to do with stream
			MarketDataListener marketDataListener = (messageType, message) ->{
				if(messageType == MarketDataMessageType.QUOTE && quoteThrottle == 10){
					quoteThrottle = 0;
					so.convertAndSend("/stream/candleData",new StreamData(message.toString()));
				} else if (messageType == MarketDataMessageType.BAR){
					so.convertAndSend("/stream/newCandleBar",new CandleBarData(message.toString()));
				}

				// update quote throttler
				if (quoteThrottle != 10) {
					quoteThrottle++;
				}

				System.out.println(message.toString());

			};
			api.cryptoMarketDataStreaming().setListener(marketDataListener);
			// subscribe to messages from socket
			api.cryptoMarketDataStreaming().subscribeToControl(
					MarketDataMessageType.SUCCESS,
					MarketDataMessageType.SUBSCRIPTION,
					MarketDataMessageType.ERROR);

			connectToAlpaca();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

    private void connectToAlpaca(){
		try{
			// Connect the websocket and confirm authentication
			api.cryptoMarketDataStreaming().connect();
			api.cryptoMarketDataStreaming().waitForAuthorization(5, TimeUnit.SECONDS);
			if (!api.cryptoMarketDataStreaming().isValid()) {
				System.out.println("Websocket not valid!");
			} else {
				// Listen to tickers and all bars via the wildcard operator ('*').
				api.cryptoMarketDataStreaming().subscribe(
						null,
						Arrays.asList("BTCUSD"),
						Arrays.asList(this.primaryStream));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
    }

    public void addTickersToStream(List<String> tickers){
        subscribedTickers.addAll(tickers);
    }

    public void removeTickerFromStream(String ticker){
        subscribedTickers.remove(ticker);
    }

	public void changePrimaryStream(String newTicker){
		this.primaryStream = newTicker;
	}

	public List<CryptoBar> getBarHistory(String ticker, CandleTimeFrame tf){
		try{
			CryptoBarsResponse barsResponse = api.cryptoMarketData().getBars(
					ticker,
					Arrays.asList(Exchange.COINBASE),
					getBarHistoryStartTime(tf),
					numBarsHistory,
					null,
					4,
					mapTimeframe(tf));


			return barsResponse.getBars();

		} catch (Exception e){
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	private BarTimePeriod mapTimeframe(CandleTimeFrame tf){
		return switch (tf) {
			case m1 -> BarTimePeriod.MINUTE;
			case h1 -> BarTimePeriod.HOUR;
			default -> BarTimePeriod.DAY;
		};
	}

	private ZonedDateTime getBarHistoryStartTime(CandleTimeFrame tf){
		ZonedDateTime now = ZonedDateTime.now();
		return switch (tf) {
			case m1 -> now.minusMinutes(numBarsHistory);
			case h1 -> now.minusHours(numBarsHistory);
			default -> now.minusDays(numBarsHistory);
		};

	}
}
