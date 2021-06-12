package pl.investadvisor.datascraper.service.commodityprice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.model.CommodityPrice;
import pl.investadvisor.datascraper.repository.CommodityPriceRepository;
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
    private CommodityPriceRepository commodityPriceRepository;
    private CommodityRepository commodityRepository;

    public void scrapePrices() {
        List<Commodity> commodities = commodityRepository.getAllCommodities();

        List<CommodityPrice> commodityPrices = new ArrayList();
        commodityPrices.addAll(scrapePricesFromFinage(commodities.stream()
                .filter(commodity -> FINAGE.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commodityPrices.addAll(scrapePricesFromPulsBiznesu(commodities.stream()
                .filter(commodity -> PULS_BIZNSU.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commodityPrices.addAll(scrapePricesFromYahooFinance(commodities.stream()
                .filter(commodity -> YAHOO_FINANCE.equals(commodity.getScrapingStrategy()))
                .collect(toList())));
        commodityPriceRepository.saveCommodityPrices(commodityPrices);
    }

    // TODO rewrite below methods to streams
    private List<CommodityPrice> scrapePricesFromFinage(List<Commodity> commodities) {
        List<CommodityPrice> commodityPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            CommodityPrice commodityPrice;
            if (CRYPTO.equals(commodity.getCommodityType())) {
                commodityPrice = finageService.getCryptocurrencyPrice(commodity);
            } else {
                commodityPrice = finageService.getEtfPrice(commodity);
            }
            if (nonNull(commodityPrice)) {
                commodityPrices.add(commodityPrice);
            }
        }
        return commodityPrices;
    }

    private List<CommodityPrice> scrapePricesFromPulsBiznesu(List<Commodity> commodities) {
        List<CommodityPrice> commodityPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            CommodityPrice stockPrice = pulsBiznesuService.getStockPrice(commodity);
            if (nonNull(stockPrice)) {
                commodityPrices.add(stockPrice);
            }
        }
        return commodityPrices;
    }

    private List<CommodityPrice> scrapePricesFromYahooFinance(List<Commodity> commodities) {
        List<CommodityPrice> commodityPrices = new ArrayList();
        for (Commodity commodity : commodities) {
            log.info("starting scrape/fetch " + commodity);
            CommodityPrice stockPrice = yahooFinanceService.getStockPrice(commodity);
            if (nonNull(stockPrice)) {
                commodityPrices.add(stockPrice);
            }
        }
        return commodityPrices;
    }
}
