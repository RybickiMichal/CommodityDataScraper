package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class Scheduler {

    YahooFinanceFetchDataService yahooFinanceFetchDataService;

    @Scheduled(fixedRate = 10000)
    void printStockPrice() {
        System.out.println(yahooFinanceFetchDataService.getYahooFinanceData("AMZN"));
    }
}
