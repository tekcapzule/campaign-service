package com.tekcapsule.campaign.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.campaign.domain.model.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Campaign findBy(String code) {
        return dynamo.load(Campaign.class, code);
    }

    @Override
    public Campaign save(Campaign campaign) {
        dynamo.save(campaign);
        return campaign;
    }
}
