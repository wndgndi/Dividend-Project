package com.example.dividendproject.scraper;

import com.example.dividendproject.constants.Month;
import com.example.dividendproject.model.Company;
import com.example.dividendproject.model.Dividend;
import com.example.dividendproject.model.ScrapedResult;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YahooFinanceScraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final long START_TIME = 86400;   // 60 * 60 * 24

    public ScrapedResult scrap(Company company) {
        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);
        try {
            long start = 0;
            long end = System.currentTimeMillis() / 1000;
            String url = String.format(STATISTICS_URL, company.getTicker(), start, end);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);  // Table 전체

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends = new ArrayList<>();
            for(Element e: tbody.children()) {
                String txt = e.text();
                if(!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.parseInt(splits[1].replace(",", ""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];

                if(month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }

                dividends.add(Dividend.builder()
                    .date(LocalDateTime.of(year, month, day, 0, 0))
                    .dividend(dividend)
                    .build());

//                System.out.println(year + "/" + month + "/" + day + " -> " + dividend);
            }
            scrapResult.setDividendEntities(dividends);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scrapResult;
    }
}