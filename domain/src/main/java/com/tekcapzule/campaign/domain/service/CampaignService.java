package com.tekcapzule.campaign.domain.service;

import com.tekcapzule.campaign.domain.command.CreateCampaignCommand;
import com.tekcapzule.campaign.domain.command.UpdateCampaignCommand;
import com.tekcapzule.campaign.domain.model.Campaign;

import java.util.List;


public interface CampaignService {

    void create(CreateCampaignCommand createCommand);
    void update(UpdateCampaignCommand updateCommand);
    List<Campaign> findAll();
    Campaign findById(String campaignId);
}
