package com.tekcapsule.marketing.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.marketing.domain.model.Campaign;

import java.util.List;

public interface CampaignDynamoRepository extends CrudRepository<Campaign, String> {

    List<Campaign> findAllByTopicCode(String topicCode);
}
