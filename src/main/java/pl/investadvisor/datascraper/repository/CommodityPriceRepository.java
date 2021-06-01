package pl.investadvisor.datascraper.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.investadvisor.datascraper.model.CommodityPrice;

import java.util.List;

@AllArgsConstructor
@Repository
public class CommodityPriceRepository {

    private DynamoDBMapper dynamoDBMapper;

    public List<CommodityPrice> saveCommodityPrices(List<CommodityPrice> commodityPrices){
        commodityPrices.forEach(commodityPrice -> dynamoDBMapper.save(commodityPrice));
        return commodityPrices;
    }
}
