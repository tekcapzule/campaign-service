package com.tekcapsule.marketing.domain.service;

import com.tekcapsule.marketing.domain.command.CreateCommand;
import com.tekcapsule.marketing.domain.command.UpdateCommand;
import com.tekcapsule.marketing.domain.model.Campaign;
import com.tekcapsule.marketing.domain.model.Status;
import com.tekcapsule.marketing.domain.repository.CampaignDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class CampaignServiceImpl implements CampaignService {
    private CampaignDynamoRepository campaignDynamoRepository;

    @Autowired
    public CampaignServiceImpl(CampaignDynamoRepository campaignDynamoRepository) {
        this.campaignDynamoRepository = campaignDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create campaign service - Module Code :%s", createCommand.getTopicCode()));

        Campaign campaign = Campaign.builder()
                .title(createCommand.getTitle())
                .topicCode(createCommand.getTopicCode())
                .author(createCommand.getAuthor())
                .publisher(createCommand.getPublisher())
                .duration(createCommand.getDuration())
                .resourceUrl(createCommand.getResourceUrl())
                .summary(createCommand.getSummary())
                .description(createCommand.getDescription())
                .modules(createCommand.getModules())
                .prizingModel(createCommand.getPrizingModel())
                .offerType(createCommand.getOfferType())
                .learningMode(createCommand.getCampaignType())
                .imageUrl(createCommand.getImageUrl())
                .promotion(createCommand.getPromotion())
                .status(Status.ACTIVE)
                .recommendations(createCommand.getRecommendations())
                .build();

        campaign.setAddedOn(createCommand.getExecOn());
        campaign.setAddedBy(createCommand.getExecBy().getUserId());

        campaignDynamoRepository.save(campaign);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update campaign service - Campaign ID:%s", updateCommand.getCourseId()));

        Campaign campaign = campaignDynamoRepository.findBy(updateCommand.getCourseId());
        if (campaign != null) {
            campaign.setTitle(updateCommand.getTitle());
            campaign.setTopicCode(updateCommand.getTopicCode());
            campaign.setAuthor(updateCommand.getAuthor());
            campaign.setPublisher(updateCommand.getPublisher());
            campaign.setDuration(updateCommand.getDuration());
            campaign.setResourceUrl(updateCommand.getResourceUrl());
            campaign.setSummary(updateCommand.getSummary());
            campaign.setDescription(updateCommand.getDescription());
            campaign.setModules(updateCommand.getModules());
            campaign.setPrizingModel(updateCommand.getPrizingModel());
            campaign.setOfferType(updateCommand.getOfferType());
            campaign.setCampaignType(updateCommand.getCampaignType());
            campaign.setPromotion(updateCommand.getPromotion());
            campaign.setImageUrl(updateCommand.getImageUrl());
            campaign.setUpdatedOn(updateCommand.getExecOn());
            campaign.setUpdatedBy(updateCommand.getExecBy().getUserId());
            campaign.setRecommendations(updateCommand.getRecommendations());
            campaignDynamoRepository.save(campaign);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend campaign service -  Campaign Id:%s", recommendCommand.getCourseId()));

        Campaign campaign = campaignDynamoRepository.findBy(recommendCommand.getCourseId());
        if (campaign != null) {
            Integer recommendationsCount = campaign.getRecommendations();
            recommendationsCount += 1;
            campaign.setRecommendations(recommendationsCount);

            campaign.setUpdatedOn(recommendCommand.getExecOn());
            campaign.setUpdatedBy(recommendCommand.getExecBy().getUserId());

            campaignDynamoRepository.save(campaign);
        }
    }

   /* @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Module Code:%s", disableCommand.getCode()));

        campaignDynamoRepository.findBy(disableCommand.getCode());
        Module topic = campaignDynamoRepository.findBy(disableCommand.getCode());
        if (topic != null) {
            topic.setStatus("INACTIVE");
            topic.setUpdatedOn(disableCommand.getExecOn());
            topic.setUpdatedBy(disableCommand.getExecBy().getUserId());
            campaignDynamoRepository.save(topic);
        }
    }*/

    @Override
    public List<Campaign> findAll() {

        log.info("Entering findAll Campaign service");

        return campaignDynamoRepository.findAll();
    }

    @Override
    public List<Campaign> findAllByTopicCode(String topicCode) {

        log.info(String.format("Entering findAllByTopicCode Campaign service - Module code:%s", topicCode));

        return campaignDynamoRepository.findAllByTopicCode(topicCode);
    }


}
