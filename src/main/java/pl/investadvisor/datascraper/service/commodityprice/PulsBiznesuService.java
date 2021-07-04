package pl.investadvisor.datascraper.service.commodityprice;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.Commodity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class PulsBiznesuService {

    public Commodity setNewStockPrice(Commodity commodity) {
        try {
            return scrapeStockPrice(commodity);
        } catch (IOException exception) {
            throw new NoDataException("No data from Puls Biznesu for " + commodity.getIndex());
        }
    }

    public Commodity setNewMetalOrResourcePrice(Commodity commodity) {
        try {
            return scrapeMetalOrResourcePrice(commodity);
        } catch (IOException exception) {
            throw new NoDataException("No data from Puls Biznesu for " + commodity.getIndex());
        }
    }

    private Commodity scrapeStockPrice(Commodity commodity) throws IOException {
        Document document;
        try {
            document = Jsoup.connect(commodity.getDataSource()).get();
        } catch (Exception exception) {
            log.error("Wrong index or website for stock " + commodity);
            return null;
        }

        String price = document.getElementsByClass("profilLast").text()
                .replaceAll("[^0-9.,]", "")
                .replaceAll(",", ".");

        commodity.setPrice(new BigDecimal(price));
        commodity.setLastScrapingDate(new Date());
        commodity.setCurrency("PLN");

        return commodity;
    }

    private Commodity scrapeMetalOrResourcePrice(Commodity commodity) throws IOException {
        Document document;
        try {
            document = Jsoup.connect(commodity.getDataSource()).get();
        } catch (Exception exception) {
            log.error("Wrong index or website for stock " + commodity);
            return null;
        }
        String priceAndCurrency = document.getElementsByClass("profilLast").text();

        String price = priceAndCurrency
                .replaceAll("[^0-9.,]", "")
                .replaceAll(",", ".");
        String currency = priceAndCurrency.substring(document.getElementsByClass("profilLast").text().indexOf(' '))
                .replaceAll("[0-9.]", "")
                .replace(",", "")
                .trim();
        commodity.setPrice(new BigDecimal(price));
        commodity.setLastScrapingDate(new Date());
        commodity.setCurrency(currency);

        return commodity;
    }
}
