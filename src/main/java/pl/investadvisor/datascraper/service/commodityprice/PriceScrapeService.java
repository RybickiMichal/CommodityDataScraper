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
import static pl.investadvisor.datascraper.model.CommodityType.CRYPTO;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.FINAGE;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.PULS_BIZNSU;
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
        commoditiesWithPrices.addAll(scrapePricesFromFinage(commodities.stream()
                .filter(commodity -> FINAGE.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commoditiesWithPrices.addAll(scrapePricesFromPulsBiznesu(commodities.stream()
                .filter(commodity -> PULS_BIZNSU.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commoditiesWithPrices.addAll(scrapePricesFromYahooFinance(commodities.stream()
                .filter(commodity -> YAHOO_FINANCE.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commodityRepository.saveCommodities(commoditiesWithPrices);
    }

    // TODO rewrite below methods to streams
    private List<Commodity> scrapePricesFromFinage(List<Commodity> commodities) {
        List<Commodity> commoditiesWithPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            Commodity commodityWithPrice;
            if (CRYPTO.equals(commodity.getCommodityType())) {
                commodityWithPrice = finageService.getCryptocurrencyPrice(commodity);
            } else {
                commodityWithPrice = finageService.getEtfPrice(commodity);
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
            Commodity stockPrice = pulsBiznesuService.getStockPrice(commodity);
            if (nonNull(stockPrice)) {
                commoditiesWithPrices.add(stockPrice);
            }
        }
        return commoditiesWithPrices;
    }

    private List<Commodity> scrapePricesFromYahooFinance(List<Commodity> commodities) {
        List<Commodity> commoditiesWithPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            Commodity stockPrice = yahooFinanceService.getStockPrice(commodity);
            if (nonNull(stockPrice)) {
                commoditiesWithPrices.add(stockPrice);
            }
        }
        return commoditiesWithPrices;
    }
}
