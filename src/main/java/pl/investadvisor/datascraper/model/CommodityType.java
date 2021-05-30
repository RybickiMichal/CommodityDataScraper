package pl.investadvisor.datascraper.model;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public enum CommodityType {
    STOCK, ETF, CRYPTO, METAL, RAW_MATERIAL;

    public static CommodityType parse(String rawType) {
        if (isNull(rawType)) {
            throw new RuntimeException("Can't parse field to enum. Field is empty.");
        }

        for (CommodityType type : CommodityType.values()) {
            if (type.name().equals(rawType)) {
                return type;
            }
        }

        throw new RuntimeException(format("Can't parse %s to enum.", rawType));
    }
}
