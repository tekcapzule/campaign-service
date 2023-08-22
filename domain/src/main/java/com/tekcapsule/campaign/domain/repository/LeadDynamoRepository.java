package com.tekcapsule.campaign.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.campaign.domain.model.Lead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Lead findBy(String id) {
        return dynamo.load(Lead.class, id);
    }

    @Override
    public List<Lead> findAll() {

        return dynamo.scan(Lead.class,new DynamoDBScanExpression());
    }

    @Override
    public Lead save(Lead lead) {
        dynamo.save(lead);
        return lead;
    }
}
