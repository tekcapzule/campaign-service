package com.tekcapzule.campaign.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.campaign.application.config.AppConfig;
import com.tekcapzule.campaign.application.function.input.GetCampaignInput;
import com.tekcapzule.campaign.domain.model.Campaign;
import com.tekcapzule.campaign.domain.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetCampaignFunction implements Function<Message<GetCampaignInput>, Message<Campaign>> {

    private final CampaignService campaignService;

    private final AppConfig appConfig;

    public GetCampaignFunction(final CampaignService campaignService, final AppConfig appConfig) {
        this.campaignService = campaignService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<Campaign> apply(Message<GetCampaignInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Campaign campaign = new Campaign();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetCampaignInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get campaign Function -Campaign Id:%s", getInput.getCampaignId()));
            campaign = campaignService.findById(getInput.getCampaignId());
            if (campaign==null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(campaign, responseHeaders);
    }
}