package pl.investadvisor.datascraper.model;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public enum ScrapingStrategy {
    PULS_BIZNSU, YAHOO_FINANCE, FINAGE;

    public static ScrapingStrategy parse(String rawStrategy) {
        if (isNull(rawStrategy)) {
            throw new RuntimeException("Can't parse field to enum. Field is empty.");
        }

        for (ScrapingStrategy strategy : ScrapingStrategy.values()) {
            if (strategy.name().equals(rawStrategy)) {
                return strategy;
            }
        }

        throw new RuntimeException(format("Can't parse %s to enum.", rawStrategy));
    }
}
