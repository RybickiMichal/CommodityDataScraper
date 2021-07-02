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

    public Commodity getStockPrice(Commodity commodity) {
        try {
            return scrapeStockPrice(commodity);
        } catch (IOException e) {
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
                .replaceAll(" ", "")
                .replace(",", ".")
                .replace("zł", "");

        commodity.setPrice(new BigDecimal(price));
        commodity.setLastScrapingDate(new Date());
        commodity.setCurrency("PLN");

        return commodity;
    }
}
