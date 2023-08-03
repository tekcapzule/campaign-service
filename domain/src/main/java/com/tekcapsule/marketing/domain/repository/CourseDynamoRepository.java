package com.tekcapsule.marketing.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.marketing.domain.model.Course;

import java.util.List;

public interface CourseDynamoRepository extends CrudRepository<Course, String> {

    List<Course> findAllByTopicCode(String topicCode);
}
