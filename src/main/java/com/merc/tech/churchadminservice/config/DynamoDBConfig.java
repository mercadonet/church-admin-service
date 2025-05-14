package com.merc.tech.churchadminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;



@Configuration
public class DynamoDBConfig   {

    @Bean
    public DynamoDbClient amazonDynamoDB() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeMyKeyId", "fakeSecretAccessKey")))
                .build();
    }

    protected String getAmazonDynamoDBEndpoint() {
        return "http://localhost:8000";
    }
}