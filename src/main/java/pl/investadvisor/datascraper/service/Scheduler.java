package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.model.CommodityPrice;
import pl.investadvisor.datascraper.repository.CommodityPriceRepository;

import static pl.investadvisor.datascraper.model.ScrapingStrategy.PULS_BIZNSU;

@AllArgsConstructor
@Service
@Slf4j
public class Scheduler {

    PulsBiznesuDataService pulsBiznesuDataService;

    CommodityPriceRepository commodityPriceRepository;
    YahooFinanceDataService yahooFinanceDataService;

//    //TODO move value to properties
//    @Scheduled(fixedRate = 20000)
//    void printStockPriceFromYahooFinance() {
//        CommodityPrice commodityPrice = yahooFinanceDataService.getStockPrice(Commodity.builder()
//                .commodityId("1")
//                .index("AMZN")
//                .build());
//        System.out.println(commodityPrice);
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }

//    @Scheduled(fixedRate = 20000)
//    void printStockPriceFromPulsBiznesu() {
//        CommodityPrice commodityPrice = pulsBiznesuDataService.getStockPrice(Commodity.builder()
//                .commodityId("2")
//                .index("LWB")
//                .scrapingStrategy(PULS_BIZNSU)
//                .dataSource("https://notowania.pb.pl/instrument/PLLWBGD00016/bogdanka/profil")
//                .build());
//        log.info(commodityPrice + "");
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }

    @Scheduled(fixedRate = 20000)
    void printStockPriceFromPulsBiznesu2() {
        CommodityPrice commodityPrice = pulsBiznesuDataService.getStockPrice(Commodity.builder()
                .commodityId("3")
                .index("lpp")
                .scrapingStrategy(PULS_BIZNSU)
                .dataSource("https://notowania.pb.pl/instrument/PLLPP0000011/lpp")
                .build());
        log.info(commodityPrice + "");
        commodityPriceRepository.addCommodityPrice(commodityPrice);
    }
}
