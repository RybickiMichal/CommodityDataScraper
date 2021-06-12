package pl.investadvisor.datascraper.service.commodityprice;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.model.CommodityPrice;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class PulsBiznesuService {

    public CommodityPrice getStockPrice(Commodity commodity) {
        try {
            return scrapeStockPrice(commodity);
        } catch (IOException e) {
            throw new NoDataException("No data from Puls Biznesu for " + commodity.getIndex());
        }
    }

    private CommodityPrice scrapeStockPrice(Commodity commodity) throws IOException {
        Document document;
        try {
            document = Jsoup.connect(commodity.getDataSource()).get();
        } catch (Exception exception) {
            log.error("Wrong index or website for stock " + commodity);
            return null;
        }

        String price = document.getElementsByClass("profilLast").text()
                .replaceAll(" ", "")
                .replace(",", ".")
                .replace("zł", "");
        return CommodityPrice.builder()
                .commodityId(commodity.getCommodityId())
                .price(new BigDecimal(price))
                .currency("PLN")
                .date(new Date())
                .build();
    }
}
