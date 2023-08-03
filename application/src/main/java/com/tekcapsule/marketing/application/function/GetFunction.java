package com.tekcapsule.marketing.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.marketing.application.config.AppConfig;
import com.tekcapsule.marketing.application.function.input.GetInput;
import com.tekcapsule.marketing.domain.model.Campaign;
import com.tekcapsule.marketing.domain.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetFunction implements Function<Message<GetInput>, Message<List<Campaign>>> {

    private final CampaignService campaignService;

    private final AppConfig appConfig;

    public GetFunction(final CampaignService campaignService, final AppConfig appConfig) {
        this.campaignService = campaignService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Campaign>> apply(Message<GetInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Campaign> cours = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course Function -Module Code:%s", getInput.getTopicCode()));
            cours = campaignService.findAllByTopicCode(getInput.getTopicCode());
            if (cours.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(cours, responseHeaders);
    }
}