package com.tekcapsule.marketing.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.marketing.application.config.AppConfig;
import com.tekcapsule.marketing.application.function.input.GetInput;
import com.tekcapsule.marketing.domain.model.Course;
import com.tekcapsule.marketing.domain.service.CourseService;
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
public class GetFunction implements Function<Message<GetInput>, Message<List<Course>>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public GetFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Course>> apply(Message<GetInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Course> courses = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course Function -Module Code:%s", getInput.getTopicCode()));
            courses = courseService.findAllByTopicCode(getInput.getTopicCode());
            if (courses.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(courses, responseHeaders);
    }
}