package com.tekcapsule.campaign.domain.service;

import com.tekcapsule.campaign.domain.command.CreateCampaignCommand;
import com.tekcapsule.campaign.domain.command.UpdateCampaignCommand;
import com.tekcapsule.campaign.domain.model.Campaign;

import java.util.List;


public interface CampaignService {

    void create(CreateCampaignCommand createCommand);
    void update(UpdateCampaignCommand updateCommand);
    List<Campaign> findAll();
    Campaign findById(String campaignId);
}
