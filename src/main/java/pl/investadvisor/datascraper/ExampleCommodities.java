package pl.investadvisor.datascraper;

import pl.investadvisor.datascraper.model.Commodity;

import java.util.List;

import static pl.investadvisor.datascraper.model.CommodityType.CRYPTO;
import static pl.investadvisor.datascraper.model.CommodityType.ETF;
import static pl.investadvisor.datascraper.model.CommodityType.STOCK;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.FINAGE;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.PULS_BIZNSU;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.YAHOO_FINANCE;

public class ExampleCommodities {

    public static List<Commodity> exampleCommodities() {
        return List.of(Commodity.builder()
                        .commodityId("1")
                        .index("AMZN")
                        .commodityType(STOCK)
                        .scrapingStrategy(YAHOO_FINANCE)
                        .build(),
                Commodity.builder()
                        .commodityId("2")
                        .index("LWB")
                        .scrapingStrategy(PULS_BIZNSU)
                        .dataSource("https://notowania.pb.pl/instrument/PLLWBGD00016/bogdanka/profil")
                        .commodityType(STOCK)
                        .build(),
                Commodity.builder()
                        .commodityId("3")
                        .index("lpp")
                        .scrapingStrategy(PULS_BIZNSU)
                        .dataSource("https://notowania.pb.pl/instrument/PLLPP0000011/lpp")
                        .commodityType(STOCK)
                        .build(),
                Commodity.builder()
                        .commodityId("4")
                        .index("GDXJ")
                        .scrapingStrategy(FINAGE)
                        .commodityType(ETF)
                        .build(),
                Commodity.builder()
                        .commodityId("5")
                        .index("BTC")
                        .scrapingStrategy(FINAGE)
                        .commodityType(CRYPTO)
                        .build());
    }
}
