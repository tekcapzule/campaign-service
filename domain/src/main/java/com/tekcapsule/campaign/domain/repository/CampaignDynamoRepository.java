package com.tekcapsule.campaign.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.campaign.domain.model.Campaign;

import java.util.List;

public interface CampaignDynamoRepository extends CrudRepository<Campaign, String> {

}
