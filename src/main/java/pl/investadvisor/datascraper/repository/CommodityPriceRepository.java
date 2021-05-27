package pl.investadvisor.datascraper.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.investadvisor.datascraper.model.CommodityPrice;

@AllArgsConstructor
@Repository
public class CommodityPriceRepository {

    private DynamoDBMapper dynamoDBMapper;

    public CommodityPrice addCommodityPrice(CommodityPrice commodityPrice){
        dynamoDBMapper.save(commodityPrice);
        return commodityPrice;
    }
}
