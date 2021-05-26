package pl.investadvisor.datascraper.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class YahooFinanceData {

    private String index;
    private String name;
    private BigDecimal price;
    private String currency;
}
