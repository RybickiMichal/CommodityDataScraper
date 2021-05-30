package pl.investadvisor.datascraper.model;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public enum CommodityCountry {
    US, PL, DE, RUS, UK, CA;

    public static CommodityCountry parse(String rawCountry) {
        if (isNull(rawCountry)) {
            throw new RuntimeException("Can't parse field to enum. Field is empty.");
        }

        for (CommodityCountry country : CommodityCountry.values()) {
            if (country.name().equals(rawCountry)) {
                return country;
            }
        }

        throw new RuntimeException(format("Can't parse %s to enum.", rawCountry));
    }
}
