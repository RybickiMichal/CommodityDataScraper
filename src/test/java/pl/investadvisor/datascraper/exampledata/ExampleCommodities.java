package pl.investadvisor.datascraper.exampledata;

import pl.investadvisor.datascraper.model.Commodity;

import java.util.List;

import static pl.investadvisor.datascraper.model.CommodityType.CRYPTO;
import static pl.investadvisor.datascraper.model.CommodityType.ETF;
import static pl.investadvisor.datascraper.model.CommodityType.PL_STOCK;
import static pl.investadvisor.datascraper.model.CommodityType.US_STOCK;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.FINAGE;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.PULS_BIZNESU;
import static pl.investadvisor.datascraper.model.ScrapingStrategy.YAHOO_FINANCE;

public class ExampleCommodities {

    public static List<Commodity> exampleCommodities() {
        return List.of(Commodity.builder()
                        .commodityId("1")
                        .index("AMZN")
                        .commodityType(US_STOCK)
                        .scrapingStrategy(YAHOO_FINANCE)
                        .build(),
                Commodity.builder()
                        .commodityId("2")
                        .index("LWB")
                        .scrapingStrategy(PULS_BIZNESU)
                        .dataSource("https://notowania.pb.pl/instrument/PLLWBGD00016/bogdanka/profil")
                        .commodityType(PL_STOCK)
                        .build(),
                Commodity.builder()
                        .commodityId("3")
                        .index("lpp")
                        .scrapingStrategy(PULS_BIZNESU)
                        .dataSource("https://notowania.pb.pl/instrument/PLLPP0000011/lpp")
                        .commodityType(PL_STOCK)
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
