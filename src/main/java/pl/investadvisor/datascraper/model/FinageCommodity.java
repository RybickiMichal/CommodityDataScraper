package pl.investadvisor.datascraper.model;

import lombok.Data;

@Data
public class FinageCommodity {

    private String symbol;
    private String price;
    private String timestamp;

}
