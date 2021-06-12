package pl.investadvisor.datascraper.service.commodityprice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.model.CommodityPrice;
import pl.investadvisor.datascraper.model.CommodityType;
import pl.investadvisor.datascraper.model.FinageCommodity;

import java.math.BigDecimal;
import java.util.Date;

import static java.lang.String.format;
import static pl.investadvisor.datascraper.model.CommodityType.CRYPTO;
import static pl.investadvisor.datascraper.model.CommodityType.ETF;

@Service
@Slf4j
public class FinageService {

    public FinageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${finage.apikey}")
    private String FINAGE_APIK_KEY;

    private RestTemplate restTemplate;

    public CommodityPrice getEtfPrice(Commodity commodity) {
        String url = buildUrl(commodity.getIndex(), ETF);
        FinageCommodity finageCommodity;
        try {
            finageCommodity = restTemplate.getForObject(url, FinageCommodity.class);
        } catch (Exception exception) {
            log.error("Wrong index for ETF " + commodity);
            return null;
        }

        return CommodityPrice.builder()
                .price(new BigDecimal(finageCommodity.getPrice()))
                .currency("USD")
                .date(new Date())
                .commodityId(commodity.getCommodityId())
                .build();
    }

    public CommodityPrice getCryptocurrencyPrice(Commodity commodity) {
        String url = buildUrl(commodity.getIndex(), CRYPTO);
        FinageCommodity finageCommodity;
        try {
            finageCommodity = restTemplate.getForObject(url, FinageCommodity.class);
        } catch (Exception exception) {
            log.error("Wrong index for crypto " + commodity);
            return null;
        }

        return CommodityPrice.builder()
                .price(new BigDecimal(finageCommodity.getPrice()))
                .currency("USD")
                .date(new Date())
                .commodityId(commodity.getCommodityId())
                .build();
    }

    private String buildUrl(String index, CommodityType commodityType) {
        if (ETF.equals(commodityType)) {
            return format("https://api.finage.co.uk/last/etf/%s?apikey=%s", index, FINAGE_APIK_KEY);
        } else {
            return format("https://api.finage.co.uk/last/crypto/%susd?apikey=%s", index, FINAGE_APIK_KEY);
        }
    }
}
