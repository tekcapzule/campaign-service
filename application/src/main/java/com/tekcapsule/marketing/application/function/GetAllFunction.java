package com.tekcapsule.marketing.application.function;

import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.marketing.application.config.AppConfig;
import com.tekcapsule.marketing.domain.model.Course;
import com.tekcapsule.marketing.domain.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllFunction implements Function<Message<EmptyFunctionInput>, Message<List<Course>>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public GetAllFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Course>> apply(Message<EmptyFunctionInput> getAllInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Course> courses = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all courses Function");
            courses = courseService.findAll();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(courses, responseHeaders);
    }
}