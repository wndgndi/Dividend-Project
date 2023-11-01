package com.example.dividendproject.scheduler;

import com.example.dividendproject.model.Company;
import com.example.dividendproject.model.ScrapedResult;
import com.example.dividendproject.persist.entity.CompanyEntity;
import com.example.dividendproject.persist.entity.DividendEntity;
import com.example.dividendproject.persist.repository.CompanyRepository;
import com.example.dividendproject.persist.repository.DividendRepository;
import com.example.dividendproject.scraper.Scraper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper yahooFinanceScraper;

    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");
        // 저장된 회사 목록을 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(Company.builder()
                .name(company.getName())
                .ticker(company.getTicker())
                .build());

            // 스크래핑한 배당금 정보 중 데이터베이스에 없는 값은 저장
            scrapedResult.getDividends().stream()
                // Dividend 모델을 DividendEntity로 매핑
                .map(e -> new DividendEntity(company.getId(), e))
                // 엘리먼트를 하나씩 확인해보며, 존재하지 않으면 Dividend Repository 에 삽입
                .forEach(e -> {
                   boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                   if (!exists) {
                       this.dividendRepository.save(e);
                   }
                });

            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);  // 3초간 정지
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("scraping scheduler end");
        }

    }

}
