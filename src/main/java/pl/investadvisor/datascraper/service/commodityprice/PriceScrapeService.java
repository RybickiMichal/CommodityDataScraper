package pl.investadvisor.datascraper.service.commodityprice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.repository.CommodityRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static pl.investadvisor.datascraper.model.CommodityType.AGRO_STOCK;
import static pl.investadvisor.datascraper.model.CommodityType.CRYPTO;
import static pl.investadvisor.datascraper.model.CommodityType.MINING_ENERGY_STOCK_OR_ETF;
import static pl.investadvisor.datascraper.model.CommodityType.PL_STOCK;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.PULS_BIZNESU;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.YAHOO_FINANCE;

@AllArgsConstructor
@Service
@Slf4j
public class PriceScrapeService {

    private PulsBiznesuService pulsBiznesuService;
    private YahooFinanceService yahooFinanceService;
    private FinageService finageService;
    private CommodityRepository commodityRepository;

    public void scrapePrices() {
        List<Commodity> commodities = commodityRepository.getAllCommodities();

        List<Commodity> commoditiesWithPrices = new ArrayList();
        commoditiesWithPrices.addAll(scrapePricesFromPulsBiznesu(commodities.parallelStream()
                .filter(commodity -> PULS_BIZNESU.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commoditiesWithPrices.addAll(scrapePricesFromYahooFinance(commodities.parallelStream()
                .filter(commodity -> YAHOO_FINANCE.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
//        commoditiesWithPrices.addAll(scrapePricesFromFinage(commodities.parallelStream()
//                .filter(commodity -> FINAGE.equals(commodity.getScrapingStrategy()))
//                .collect(toList())));
//        commodityRepository.saveCommodities(commoditiesWithPrices);
    }

    // TODO rewrite below methods to streams
    private List<Commodity> scrapePricesFromFinage(List<Commodity> commodities) {
        List<Commodity> commoditiesWithPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            Commodity commodityWithPrice;
            if (CRYPTO.equals(commodity.getCommodityType())) {
                commodityWithPrice = finageService.setNewCryptocurrencyPrice(commodity);
            } else {
                commodityWithPrice = finageService.setNewEtfPrice(commodity);
            }
            if (nonNull(commodityWithPrice)) {
                commoditiesWithPrices.add(commodityWithPrice);
            }
        }
        return commoditiesWithPrices;
    }

    private List<Commodity> scrapePricesFromPulsBiznesu(List<Commodity> commodities) {
        List<Commodity> commoditiesWithPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            Commodity commodityWithActualPrice;
            if (isPolishOrRawMaterialStock(commodity)) {
                commodityWithActualPrice = pulsBiznesuService.setNewStockPrice(commodity);
            } else {
                commodityWithActualPrice = pulsBiznesuService.setNewMetalOrResourcePrice(commodity);
            }

            if (nonNull(commodityWithActualPrice.getPrice())) {
                commoditiesWithPrices.add(commodityWithActualPrice);
            }
        }
        return commoditiesWithPrices;
    }

    private boolean isPolishOrRawMaterialStock(Commodity commodity) {
        return PL_STOCK.equals(commodity.getCommodityType()) || MINING_ENERGY_STOCK_OR_ETF.equals(commodity.getCommodityType()) || AGRO_STOCK.equals(commodity.getCommodityType());
    }

    private List<Commodity> scrapePricesFromYahooFinance(List<Commodity> commodities) {
        List<Commodity> commoditiesWithPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            Commodity stockPrice = yahooFinanceService.setNewStockPrice(commodity);
            if (nonNull(stockPrice)) {
                commoditiesWithPrices.add(stockPrice);
            }
        }
        return commoditiesWithPrices;
    }
}
