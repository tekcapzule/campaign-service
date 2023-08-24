package com.tekcapsule.campaign.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.campaign.domain.model.Lead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Slf4j
@Repository
public class LeadDynamoRepository implements LeadRepository{
    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public LeadDynamoRepository(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public Lead findBy(String campaignId) {
        return dynamo.load(Lead.class, campaignId);
    }

    @Override
    public List<Lead> findAll() {
        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<Lead> queryExpression = new DynamoDBQueryExpression<Lead>()
                .withIndexName("leadGSI")
                .withConsistentRead(false)
                .withKeyConditionExpression("#status = :status")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Lead.class, queryExpression);
    }

    @Override
    public Lead save(Lead lead) {
        dynamo.save(lead);
        return lead;
    }
}
