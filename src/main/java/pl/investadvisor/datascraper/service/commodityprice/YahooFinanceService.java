package pl.investadvisor.datascraper.service.commodityprice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.exception.NoDataException;
import pl.investadvisor.datascraper.model.Commodity;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;
import static java.util.Objects.isNull;
import static org.joda.time.LocalDate.fromCalendarFields;
import static yahoofinance.histquotes.Interval.DAILY;

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
        List<HistoricalQuote> tenYearsHistoricalQuoteDaily = stock.getHistory(
                new GregorianCalendar(getInstance().get(YEAR) - 10, getInstance().get(MONTH), 1), DAILY);
        commodity.setLowOneYear(getLow(tenYearsHistoricalQuoteDaily, 1));
        commodity.setLowThreeYears(getLow(tenYearsHistoricalQuoteDaily, 3));
        commodity.setLowFiveYears(getLow(tenYearsHistoricalQuoteDaily, 5));
        commodity.setLowTenYears(getLow(tenYearsHistoricalQuoteDaily, 10));

        commodity.setHighOneYear(getHigh(tenYearsHistoricalQuoteDaily, 1));
        commodity.setHighThreeYears(getHigh(tenYearsHistoricalQuoteDaily, 3));
        commodity.setHighFiveYears(getHigh(tenYearsHistoricalQuoteDaily, 5));
        commodity.setHighTenYears(getHigh(tenYearsHistoricalQuoteDaily, 10));
    }

    private BigDecimal getHigh(List<HistoricalQuote> tenYearsHistoricalQuoteWeekly, int years) {
        return tenYearsHistoricalQuoteWeekly.parallelStream()
                .filter(historicalQuote -> historicalQuote.getDate().getTime().after(fromCalendarFields(
                        new GregorianCalendar(getInstance().get(YEAR) - years, getInstance().get(MONTH), 1))
                        .toDate()))
                .map(historicalQuote -> historicalQuote.getHigh())
                .max(Comparator.comparing(BigDecimal::toBigInteger))
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getLow(List<HistoricalQuote> tenYearsHistoricalQuoteWeekly, int years) {
        return tenYearsHistoricalQuoteWeekly.parallelStream()
                .filter(historicalQuote -> historicalQuote.getDate().getTime().after(fromCalendarFields(
                        new GregorianCalendar(getInstance().get(YEAR) - years, getInstance().get(MONTH), 1))
                        .toDate()))
                .map(historicalQuote -> historicalQuote.getLow())
                .min(Comparator.comparing(BigDecimal::toBigInteger))
                .orElse(BigDecimal.ZERO);
    }


}
