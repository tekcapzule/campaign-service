package com.tekcapzule.campaign.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.campaign.application.config.AppConfig;
import com.tekcapzule.campaign.application.function.input.UpdateCampaignInput;
import com.tekcapzule.campaign.application.mapper.InputOutputMapper;
import com.tekcapzule.campaign.domain.command.UpdateCampaignCommand;
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
public class UpdateCampaignFunction implements Function<Message<UpdateCampaignInput>, Message<Void>> {

    private final CampaignService campaignService;

    private final AppConfig appConfig;

    public UpdateCampaignFunction(final CampaignService campaignService, final AppConfig appConfig) {
        this.campaignService = campaignService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<UpdateCampaignInput> updateInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            UpdateCampaignInput updateInput = updateInputMessage.getPayload();
            log.info(String.format("Entering update campaign Function - Campaign ID:%s", updateInput.getCampaignId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());
            UpdateCampaignCommand updateCommand = InputOutputMapper.buildUpdateCampaignCommandFromUpdateCampaignInput.apply(updateInput, origin);
            campaignService.update(updateCommand);
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