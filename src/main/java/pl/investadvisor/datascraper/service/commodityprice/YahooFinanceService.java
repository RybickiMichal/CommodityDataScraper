package pl.investadvisor.datascraper.service.commodityprice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.Commodity;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;
import static java.util.Objects.isNull;
import static yahoofinance.histquotes.Interval.MONTHLY;
import static yahoofinance.histquotes.Interval.WEEKLY;

@Service
@Slf4j
public class YahooFinanceService {

    private Stock stock;

    public Commodity setNewStockPrice(Commodity commodity) {
        try {
            return fetchStockPrice(commodity);
        } catch (IOException exception) {
            throw new NoDataException("No data from Yahoo Finance for " + commodity.getIndex());
        }
    }

    private Commodity fetchStockPrice(Commodity commodity) throws IOException {
        stock = YahooFinance.get(commodity.getIndex());
        if (isNull(stock)) {
            log.error("Wrong index for stock " + commodity);
            return null;
        }
        setHighLow(commodity, stock);

        commodity.setPrice(stock.getQuote().getPrice());
        commodity.setLastScrapingDate(new Date());
        commodity.setCurrency(stock.getCurrency());
        return commodity;
    }

    private void setHighLow(Commodity commodity, Stock stock) throws IOException {
        commodity.setLowOneYear(getLow(stock, 1, WEEKLY));
        commodity.setLowThreeYears(getLow(stock, 3, WEEKLY));
        commodity.setLowFiveYears(getLow(stock, 5, MONTHLY));
        commodity.setLowTenYears(getLow(stock, 10, MONTHLY));

        commodity.setHighOneYear(getHigh(stock, 1, WEEKLY));
        commodity.setHighThreeYears(getHigh(stock, 3, WEEKLY));
        commodity.setHighFiveYears(getHigh(stock, 5, MONTHLY));
        commodity.setHighTenYears(getHigh(stock, 10, MONTHLY));
    }

    private BigDecimal getHigh(Stock stock, int years, Interval interval) throws IOException {
        return stock.getHistory(new GregorianCalendar(getInstance().get(YEAR) - years, getInstance().get(MONTH), 1), interval).stream()
                .map(historicalQuote -> historicalQuote.getLow())
                .max(Comparator.comparing(BigDecimal::toBigInteger))
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getLow(Stock stock, int years, Interval interval) throws IOException {
        return stock.getHistory(new GregorianCalendar(getInstance().get(YEAR) - years, getInstance().get(MONTH), 1), interval).stream()
                .map(historicalQuote -> historicalQuote.getLow())
                .min(Comparator.comparing(BigDecimal::toBigInteger))
                .orElse(BigDecimal.ZERO);
    }


}
