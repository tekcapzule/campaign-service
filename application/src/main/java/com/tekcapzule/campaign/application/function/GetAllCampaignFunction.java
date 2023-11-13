package com.tekcapzule.campaign.application.function;

import com.tekcapzule.campaign.application.config.AppConfig;
import com.tekcapzule.core.domain.EmptyFunctionInput;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.campaign.domain.model.Campaign;
import com.tekcapzule.campaign.domain.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllCampaignFunction implements Function<Message<EmptyFunctionInput>, Message<List<Campaign>>> {

    private final CampaignService campaignService;

    private final AppConfig appConfig;

    public GetAllCampaignFunction(final CampaignService campaignService, final AppConfig appConfig) {
        this.campaignService = campaignService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Campaign>> apply(Message<EmptyFunctionInput> getAllInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Campaign> campaigns = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all campaign Function");
            campaigns = campaignService.findAll();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(campaigns, responseHeaders);
    }
}