package com.tekcapsule.marketing.domain.service;

import com.tekcapsule.marketing.domain.command.CreateCommand;
import com.tekcapsule.marketing.domain.command.RecommendCommand;
import com.tekcapsule.marketing.domain.command.UpdateCommand;
import com.tekcapsule.marketing.domain.model.Course;

import java.util.List;


public interface CourseService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<Course> findAll();

    List<Course> findAllByTopicCode(String code);
    void recommend(RecommendCommand recommendCommand);
}
