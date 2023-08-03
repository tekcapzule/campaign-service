package com.tekcapsule.marketing.application.function;

import com.tekcapsule.marketing.application.config.AppConfig;
import com.tekcapsule.marketing.application.function.input.RecommendInput;
import com.tekcapsule.marketing.application.mapper.InputOutputMapper;
import com.tekcapsule.marketing.domain.command.RecommendCommand;
import com.tekcapsule.marketing.domain.service.CourseService;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class RecommendFunction implements Function<Message<RecommendInput>, Message<Void>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public RecommendFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<Void> apply(Message<RecommendInput> recommendInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            RecommendInput recommendInput = recommendInputMessage.getPayload();
            log.info(String.format("Entering recommend course Function -  Course Id:%s", recommendInput.getCourseId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(recommendInputMessage.getHeaders());
            RecommendCommand recommendCommand = InputOutputMapper.buildRecommendCommandFromRecommendInput.apply(recommendInput, origin);
            courseService.recommend(recommendCommand);
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