package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.repository.CommodityPriceRepository;

@AllArgsConstructor
@Service
@Slf4j
public class Scheduler {

    PulsBiznesuDataService pulsBiznesuDataService;
    CommodityPriceRepository commodityPriceRepository;
    YahooFinanceDataService yahooFinanceDataService;
    FinageDataService finageDataService;

//    @Scheduled(fixedRate = 20000)
//    void printStockPriceFromYahooFinance() {
//        CommodityPrice commodityPrice = yahooFinanceDataService.getStockPrice(Commodity.builder()
//                .commodityId("1")
//                .index("AMZN")
//    .commodityType(STOCK)
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
//    .commodityType(STOCK)
//                .build());
//        log.info(commodityPrice + "");
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }

//    @Scheduled(fixedRate = 20000)
//    void printStockPriceFromPulsBiznesu2() {
//        CommodityPrice commodityPrice = pulsBiznesuDataService.getStockPrice(Commodity.builder()
//                .commodityId("3")
//                .index("lpp")
//                .scrapingStrategy(PULS_BIZNSU)
//                .dataSource("https://notowania.pb.pl/instrument/PLLPP0000011/lpp")
//                .commodityType(STOCK)
//                .build());
//        log.info(commodityPrice + "");
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }

//    @Scheduled(fixedRate = 20000)
//    void printEtfPriceFromFinage() {
//        CommodityPrice commodityPrice = finageDataService.getEtfPrice(Commodity.builder()
//                .commodityId("4")
//                .index("GDXJ")
//                .scrapingStrategy(FINAGE)
//                .commodityType(ETF)
//                .build());
//        log.info(commodityPrice + "");
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }

//    @Scheduled(fixedRate = 20000)
//    void printCryptocurrencyPriceFromFinage() {
//        CommodityPrice commodityPrice = finageDataService.getCryptocurrencyPrice(Commodity.builder()
//                .commodityId("5")
//                .index("BTC")
//                .scrapingStrategy(FINAGE)
//                .commodityType(CRYPTO)
//                .build());
//        log.info(commodityPrice + "");
//        commodityPriceRepository.addCommodityPrice(commodityPrice);
//    }
}
