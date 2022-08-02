package com.example.tradingplatformbackend;

import com.example.tradingplatformbackend.DataApi.CandleDataApi;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.realtime.enums.MarketDataMessageType;
import net.jacobpeterson.alpaca.websocket.marketdata.MarketDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TradingPlatformBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformBackendApplication.class, args);
	}
}

