package com.example.dividendproject.scraper;

import com.example.dividendproject.model.Company;
import com.example.dividendproject.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
