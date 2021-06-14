package pl.investadvisor.datascraper.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.investadvisor.datascraper.model.Commodity;

import java.util.List;

@AllArgsConstructor
@Repository
public class CommodityRepository {

    private DynamoDBMapper dynamoDBMapper;

    public List<Commodity> getAllCommodities() {
        return dynamoDBMapper.scan(Commodity.class, new DynamoDBScanExpression());
    }

}
