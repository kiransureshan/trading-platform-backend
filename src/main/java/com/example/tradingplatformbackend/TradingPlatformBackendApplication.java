package com.example.tradingplatformbackend;


import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Repositories.WatchlistRepo;
import com.example.tradingplatformbackend.Repositories.WatchlistTickerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class TradingPlatformBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(WatchlistTickerRepo trepo, WatchlistRepo wlrepo)
	{
		return args -> {
			// wipe watchlist db
			for (Watchlist wl : wlrepo.findAll()){
				wlrepo.deleteById(wl.getId());
			}
		};
	}
}

