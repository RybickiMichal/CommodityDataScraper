package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.model.CommodityPrice;
import pl.investadvisor.datascraper.repository.CommodityPriceRepository;

@AllArgsConstructor
@Service
public class Scheduler {

    CommodityPriceRepository commodityPriceRepository;
    YahooFinanceFetchDataService yahooFinanceFetchDataService;

    //TODO move value to properties
    @Scheduled(fixedRate = 20000)
    void printStockPrice() {
        CommodityPrice exampleCommodityPrice = yahooFinanceFetchDataService.getYahooFinanceData("AMZN");
        System.out.println(exampleCommodityPrice);
        commodityPriceRepository.addCommodityPrice(exampleCommodityPrice);
    }
}
