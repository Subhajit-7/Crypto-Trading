package com.subhajit.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CoinDto {

    private String id;
    private String symbol;
    private String name;
    private String image;
    private double currentPrice;
    private double marketCap;
    private double marketCapRank;
    private double totalVolume;
    private double high24h;
    private double low24h;
    private double priceChange24h;
    private double priceChangePercentage24h;
    private double marketCapChange24h;
    private double marketCapChangePercentage24h;
    private double circulatingSupply;
    private double totalSupply;
    private long ath;
    private long athChangePercentage;
    private Date athDate;
    private long atl;
    private long atlChangePercentage;
    private Date atlDate;
    private Date lastUpdated;
}
