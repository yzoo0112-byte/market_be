package com.market_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//
@SpringBootApplication

public class MarketBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketBeApplication.class, args);
    }

}
