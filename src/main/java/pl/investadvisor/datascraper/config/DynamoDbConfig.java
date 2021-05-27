package pl.investadvisor.datascraper.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {


    @Value("${aws.dynamodb.service.endpoint}")
    private String SERVICE_ENDPOINT;

    @Value("${aws.dynamodb.signing.region}")
    private String SIGNING_REGION;

    @Value("${aws.dynamodb.accesskey}")
    private String ACCESS_KEY;

    @Value("${aws.dynamodb.secretkey}")
    private String SECRET_KEY;


    @Bean
    public DynamoDBMapper mapper() {
        return new DynamoDBMapper(amazonDynamoDbConfig());
    }

    private AmazonDynamoDB amazonDynamoDbConfig() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, SIGNING_REGION))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .build();
    }
}
