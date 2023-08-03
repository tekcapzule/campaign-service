package com.tekcapsule.campaign.domain.service;

import com.tekcapsule.campaign.domain.command.CreateCommand;
import com.tekcapsule.campaign.domain.command.UpdateCommand;
import com.tekcapsule.campaign.domain.model.Campaign;

import java.util.List;


public interface CampaignService {

    void create(CreateCommand createCommand);
    void update(UpdateCommand updateCommand);
    List<Campaign> findAll();
    Campaign findById(String campaignId);
}
