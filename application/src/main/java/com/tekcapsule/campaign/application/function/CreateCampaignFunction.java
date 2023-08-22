package com.tekcapsule.campaign.application.function;

import com.tekcapsule.campaign.application.config.AppConfig;
import com.tekcapsule.campaign.application.function.input.CreateCampaignInput;
import com.tekcapsule.campaign.application.mapper.InputOutputMapper;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.campaign.domain.command.CreateCampaignCommand;
import com.tekcapsule.campaign.domain.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class CreateCampaignFunction implements Function<Message<CreateCampaignInput>, Message<Void>> {

    private final CampaignService campaignService;

    private final AppConfig appConfig;

    public CreateCampaignFunction(final CampaignService campaignService, final AppConfig appConfig) {
        this.campaignService = campaignService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateCampaignInput> createInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            CreateCampaignInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create campaign Function - Campaign Title:%s", createInput.getTitle()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateCampaignCommand createCommand = InputOutputMapper.buildCreateCampaignCommandFromCreateCampaignInput.apply(createInput, origin);
            campaignService.create(createCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}