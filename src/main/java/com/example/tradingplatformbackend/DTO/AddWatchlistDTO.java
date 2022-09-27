package com.example.tradingplatformbackend.DTO;


import java.util.UUID;

public class AddWatchlistDTO {
    public UUID id;
    public String name;

    public AddWatchlistDTO(String name, UUID id){
        this.id = id;
        this.name = name;
    }
}
