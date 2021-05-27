package pl.investadvisor.datascraper.service;

import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.CommodityPrice;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Service
public class YahooFinanceFetchDataService {

    private Stock stock;

    public CommodityPrice getYahooFinanceData(String index) {
        try {
            return fetchYahooFinanceData(index);
        } catch (IOException e) {
            throw new NoDataException("No data from Yahoo Finance for " + index);
        }
    }

    private CommodityPrice fetchYahooFinanceData(String index) throws IOException {
        stock = YahooFinance.get(index);
        return CommodityPrice.builder()
                .index(index)
                .name(stock.getName())
                .price(stock.getQuote().getPrice())
                .currency(stock.getCurrency())
                .build();
    }
}
