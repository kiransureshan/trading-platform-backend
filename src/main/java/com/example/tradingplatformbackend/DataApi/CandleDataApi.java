package com.example.tradingplatformbackend.DataApi;

import com.example.tradingplatformbackend.Models.CandleBarData;
import com.example.tradingplatformbackend.Models.CandleTimeFrame;
import com.example.tradingplatformbackend.Models.StreamData;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.realtime.enums.MarketDataMessageType;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBarsResponse;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarAdjustment;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarFeed;
import net.jacobpeterson.alpaca.websocket.marketdata.MarketDataListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CandleDataApi implements Runnable{
    private final AlpacaAPI api;
    private final HashMap<String,Integer> subscribedTickers;
	private String primaryStream;
	private final SimpMessageSendingOperations so;
	private int quoteThrottle;
	private final int numBarsHistory;

    public CandleDataApi(SimpMessageSendingOperations so, String primaryTicker){
        this.api = new AlpacaAPI();
		this.so = so;
		this.primaryStream = primaryTicker;
		this.subscribedTickers = new HashMap<>();
		this.quoteThrottle = 0;
		subscribedTickers.put(primaryStream,1);
		this.numBarsHistory = 50;
    }

	@Override
	public void run() {
		try{
			if(api.stockMarketDataStreaming().isConnected()){
				return;
			}
			// tell listener what to do with stream
			MarketDataListener marketDataListener = (messageType, message) ->{
				if(messageType == MarketDataMessageType.QUOTE && quoteThrottle == 50){
					quoteThrottle = 0;
					so.convertAndSend("/stream/candleData",new StreamData(message.toString()));
					System.out.println(message);
				} else if (messageType == MarketDataMessageType.BAR){
					so.convertAndSend("/stream/newCandleBar",new CandleBarData(message.toString()));
				}

				// update quote throttler
				if (quoteThrottle != 50) {
					quoteThrottle++;
				}
			};
			api.stockMarketDataStreaming().setListener(marketDataListener);
			// subscribe to messages from socket
			api.stockMarketDataStreaming().subscribeToControl(
					MarketDataMessageType.SUCCESS,
					MarketDataMessageType.SUBSCRIPTION,
					MarketDataMessageType.ERROR);


			// Connect the websocket and confirm authentication
			api.stockMarketDataStreaming().connect();
			api.stockMarketDataStreaming().waitForAuthorization(5, TimeUnit.SECONDS);

			if (!api.stockMarketDataStreaming().isValid()) {
				throw new Exception("Websocket not valid!");
			} else {
				// Listen to tickers and all bars via the wildcard operator ('*').
				api.stockMarketDataStreaming().subscribe(
						null,
						Arrays.asList(this.primaryStream),
						Arrays.asList(this.primaryStream)
				);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void changePrimaryStream(String newTicker){
		try{
			if (!subscribedTickers.containsKey(newTicker)){
				api.stockMarketDataStreaming().subscribe(
						null,
						Arrays.asList(newTicker),
						Arrays.asList(newTicker));

				subscribedTickers.put(newTicker, 1);

				// update the ticker count
				int oldPrimaryCount = subscribedTickers.get(primaryStream);
				if (oldPrimaryCount > 1){
					subscribedTickers.put(primaryStream,oldPrimaryCount - 1);
				} else{
					subscribedTickers.remove(primaryStream);
					api.stockMarketDataStreaming().unsubscribe(
							null,
							Arrays.asList(primaryStream),
							Arrays.asList(primaryStream));
				}
				primaryStream = newTicker;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public List<StockBar> getBarHistory(String ticker, CandleTimeFrame tf){
		try{
			if(api.stockMarketDataStreaming().isConnected()){
				StockBarsResponse barsResponse = api.stockMarketData().getBars(
						ticker,
						getBarHistoryStartTime(tf),
						ZonedDateTime.now(),
						numBarsHistory,
						null,
						// number of time period ex. 4,2,1 of hour
						1,
						// timeframe to multiply by number of time period above
						mapTimeframe(tf),
						BarAdjustment.SPLIT,
						BarFeed.IEX
				);

				return barsResponse.getBars();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private BarTimePeriod mapTimeframe(CandleTimeFrame tf){
		switch (tf) {
			case m1:
				return BarTimePeriod.MINUTE;
			case h1:
				return BarTimePeriod.HOUR;
			default:
				return BarTimePeriod.DAY;
		}
	}

	private ZonedDateTime getBarHistoryStartTime(CandleTimeFrame tf){
		ZonedDateTime now = ZonedDateTime.now();
		switch (tf) {
			case m1:
				return now.minusMinutes(numBarsHistory);
			case h1:
				return now.minusHours(numBarsHistory);
			default:
				return now.minusDays(numBarsHistory);
		}


	}
}
