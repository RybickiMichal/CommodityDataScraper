package pl.investadvisor.datascraper.service;

import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoneDataException;
import pl.investadvisor.datascraper.model.YahooFinanceData;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Service
public class YahooFinanceFetchDataService {

    private Stock stock;

    public YahooFinanceData getYahooFinanceData(String index) {
        try {
            return fetchYahooFinanceData(index);
        } catch (IOException e) {
            throw new NoneDataException("None data from Yahoo Finance for " + index);
        }
    }

    private YahooFinanceData fetchYahooFinanceData(String index) throws IOException {
        stock = YahooFinance.get(index);
        return YahooFinanceData.builder()
                .index(index)
                .name(stock.getName())
                .price(stock.getQuote().getPrice())
                .currency(stock.getCurrency())
                .build();
    }
}
