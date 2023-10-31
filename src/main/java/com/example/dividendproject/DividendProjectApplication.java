package com.example.dividendproject;

import com.example.dividendproject.model.Company;
import com.example.dividendproject.scraper.Scraper;
import com.example.dividendproject.scraper.YahooFinanceScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DividendProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendProjectApplication.class, args);
    }

}
