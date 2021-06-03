package pl.investadvisor.datascraper.service.commodityprice;

import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.model.CommodityPrice;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Date;

@Service
public class YahooFinanceService {

    private Stock stock;

    public CommodityPrice getStockPrice(Commodity commodity) {
        try {
            return fetchStockPrice(commodity);
        } catch (IOException e) {
            throw new NoDataException("No data from Yahoo Finance for " + commodity.getIndex());
        }
    }

    private CommodityPrice fetchStockPrice(Commodity commodity) throws IOException {
        stock = YahooFinance.get(commodity.getIndex());
        return CommodityPrice.builder()
                .commodityId(commodity.getCommodityId())
                .price(stock.getQuote().getPrice())
                .currency(stock.getCurrency())
                .date(new Date())
                .build();
    }
}
