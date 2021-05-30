package pl.investadvisor.datascraper.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Commodity {

    @NonNull
    private String commodityId;

    @NonNull
    private String index;

    @DynamoDBTypeConvertedEnum
    @NonNull
    private ScrapingStrategy scrapingStrategy;

    private String dataSource;

//    @DynamoDBTypeConvertedEnum
//    @NonNull
//    private CommodityType commodityType;
//
//    @DynamoDBTypeConvertedEnum
//    @NonNull
//    private CommodityCountry country;

}
