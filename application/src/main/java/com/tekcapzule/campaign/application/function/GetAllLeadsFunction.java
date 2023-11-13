package com.tekcapzule.campaign.application.function;

import com.tekcapzule.campaign.application.config.AppConfig;
import com.tekcapzule.campaign.domain.model.Lead;
import com.tekcapzule.campaign.domain.service.LeadService;
import com.tekcapzule.core.domain.EmptyFunctionInput;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
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