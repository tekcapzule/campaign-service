package com.tekcapsule.campaign.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import com.tekcapsule.campaign.domain.model.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class CampaignDynamoRepository implements CampaignRepository {

    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public CampaignDynamoRepository(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Campaign> findAll() {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<Campaign> queryExpression = new DynamoDBQueryExpression<Campaign>()
                .withIndexName("campaignGSI")
                .withConsistentRead(false)
                .withKeyConditionExpression("#status = :status")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Campaign.class, queryExpression);
    }

    @Override
    public Campaign findBy(String campaignId) {
        return dynamo.load(Campaign.class, campaignId);
    }
  
  
    @Override
    public Campaign save(Campaign campaign) {
        dynamo.save(campaign);
        return campaign;
    }
}
