package com.tekcapsule.marketing.domain.service;

import com.tekcapsule.marketing.domain.command.CreateCommand;
import com.tekcapsule.marketing.domain.command.UpdateCommand;
import com.tekcapsule.marketing.domain.model.Campaign;

import java.util.List;


public interface CampaignService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<Campaign> findAll();

    List<Campaign> findAllByTopicCode(String code);
    void recommend(RecommendCommand recommendCommand);
}
