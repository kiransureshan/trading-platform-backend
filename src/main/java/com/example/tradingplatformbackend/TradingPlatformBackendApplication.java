package com.example.tradingplatformbackend;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.realtime.enums.MarketDataMessageType;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBarsResponse;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarAdjustment;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarFeed;
import net.jacobpeterson.alpaca.rest.AlpacaClientException;
import net.jacobpeterson.alpaca.websocket.marketdata.MarketDataListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TradingPlatformBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformBackendApplication.class, args);
		AlpacaAPI alpacaAPI = new AlpacaAPI();

		try{
			// tell listener what to do
			MarketDataListener marketDataListener = (messageType, message) ->
					System.out.printf("%s: %s\n", messageType.name(), message);
			alpacaAPI.stockMarketDataStreaming().setListener(marketDataListener);

			// subscribe to messages from socket
			alpacaAPI.stockMarketDataStreaming().subscribeToControl(
					MarketDataMessageType.SUCCESS,
					MarketDataMessageType.SUBSCRIPTION,
					MarketDataMessageType.ERROR);

			// Connect the websocket and confirm authentication
			alpacaAPI.stockMarketDataStreaming().connect();
			alpacaAPI.stockMarketDataStreaming().waitForAuthorization(5, TimeUnit.SECONDS);
			if (!alpacaAPI.stockMarketDataStreaming().isValid()) {
				System.out.println("Websocket not valid!");
			} else {
				// Listen to AAPL and TSLA trades and all bars via the wildcard operator ('*').
				alpacaAPI.stockMarketDataStreaming().subscribe(
						Arrays.asList("AAPL", "TSLA"),
						null,
						Arrays.asList("*"));

				// Wait a few seconds
				Thread.sleep(5000);

				// Manually disconnect the websocket
				alpacaAPI.stockMarketDataStreaming().disconnect();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

