package com.tekcapzule.campaign.application.function;

import com.tekcapzule.campaign.application.config.AppConfig;
import com.tekcapzule.campaign.application.function.input.CreateLeadInput;
import com.tekcapzule.campaign.application.mapper.InputOutputMapper;
import com.tekcapzule.campaign.domain.command.CreateLeadCommand;
import com.tekcapzule.campaign.domain.service.LeadService;
import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class CreateLeadFunction implements Function<Message<CreateLeadInput>, Message<Void>> {

    private final LeadService leadService;

    private final AppConfig appConfig;

    public CreateLeadFunction(final LeadService leadService, final AppConfig appConfig) {
        this.leadService = leadService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateLeadInput> createInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            CreateLeadInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create lead Function - Email Id:%s", createInput.getEmailId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateLeadCommand createCommand = InputOutputMapper.buildCreateLeadCommandFromCreateLeadInput.apply(createInput, origin);
            leadService.create(createCommand);
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