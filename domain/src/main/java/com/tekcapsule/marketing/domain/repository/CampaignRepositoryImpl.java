package com.tekcapsule.marketing.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.marketing.domain.model.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class CampaignRepositoryImpl implements CampaignDynamoRepository {

    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public CampaignRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Campaign> findAll() {

        return dynamo.scan(Campaign.class,new DynamoDBScanExpression());
    }

    @Override
    public List<Campaign> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<Campaign> queryExpression = new DynamoDBQueryExpression<Campaign>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Campaign.class, queryExpression);

    }

    @Override
    public Campaign findBy(String code) {
        return dynamo.load(Campaign.class, code);
    }

    @Override
    public Campaign save(Campaign campaign) {
        dynamo.save(campaign);
        return campaign;
    }
}
