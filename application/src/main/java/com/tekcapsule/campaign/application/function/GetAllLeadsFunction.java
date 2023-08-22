package com.tekcapsule.campaign.application.function;

import com.tekcapsule.campaign.application.config.AppConfig;
import com.tekcapsule.campaign.domain.model.Lead;
import com.tekcapsule.campaign.domain.service.LeadService;
import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
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
public class GetAllLeadsFunction implements Function<Message<EmptyFunctionInput>, Message<List<Lead>>> {

    private final LeadService leadService;

    private final AppConfig appConfig;

    public GetAllLeadsFunction(final LeadService leadService, final AppConfig appConfig) {
        this.leadService = leadService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Lead>> apply(Message<EmptyFunctionInput> getAllInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Lead> leads = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all leads Function");
            leads = leadService.findAll();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(leads, responseHeaders);
    }
}