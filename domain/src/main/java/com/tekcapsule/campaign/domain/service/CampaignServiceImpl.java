package com.tekcapsule.campaign.domain.service;

import com.tekcapsule.campaign.domain.repository.CampaignRepository;
import com.tekcapsule.campaign.domain.command.CreateCampaignCommand;
import com.tekcapsule.campaign.domain.command.UpdateCampaignCommand;
import com.tekcapsule.campaign.domain.model.Campaign;
import com.tekcapsule.campaign.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class CampaignServiceImpl implements CampaignService {
    private CampaignRepository campaignRepository;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignDynamoRepository) {
        this.campaignRepository = campaignDynamoRepository;
    }

    @Override
    public void create(CreateCampaignCommand createCommand) {

        log.info(String.format("Entering create campaign service - Title :%s", createCommand.getTitle()));

        Campaign campaign = Campaign.builder()
                .title(createCommand.getTitle())
                .subTitle(createCommand.getSubTitle())
                .description(createCommand.getDescription())
                .highlights(createCommand.getHighlights())
                .offer(createCommand.getOffer())
                .startsOn(createCommand.getStartsOn())
                .endsOn(createCommand.getEndsOn())
                .resourceUrl(createCommand.getResourceUrl())
                .imageUrl(createCommand.getImageUrl())
                .status(Status.ACTIVE)
                .build();

        campaign.setAddedOn(createCommand.getExecOn());
        campaign.setAddedBy(createCommand.getExecBy().getUserId());

        campaignRepository.save(campaign);
    }

    @Override
    public void update(UpdateCampaignCommand updateCommand) {

        log.info(String.format("Entering update campaign service - Campaign ID:%s", updateCommand.getCampaignId()));

        Campaign campaign = campaignRepository.findBy(updateCommand.getCampaignId());
        if (campaign != null) {
            campaign.setTitle(updateCommand.getTitle());
            campaign.setSubTitle(updateCommand.getSubTitle());
            campaign.setDescription(updateCommand.getDescription());
            campaign.setHighlights(updateCommand.getHighlights());
            campaign.setStartsOn(updateCommand.getStartsOn());
            campaign.setEndsOn(updateCommand.getEndsOn());
            campaign.setOffer(updateCommand.getOffer());
            campaign.setResourceUrl(updateCommand.getResourceUrl());
            campaign.setUpdatedOn(updateCommand.getExecOn());
            campaign.setUpdatedBy(updateCommand.getExecBy().getUserId());
            campaignRepository.save(campaign);
        }
    }

    @Override
    public List<Campaign> findAll() {

        log.info("Entering findAll Campaign service");
        return campaignRepository.findAll();
    }

    @Override
    public Campaign findById(String campaignId) {

        log.info(String.format("Entering findById Campaign service - CampaignId :%s", campaignId));

        return campaignRepository.findBy(campaignId);
    }

}
