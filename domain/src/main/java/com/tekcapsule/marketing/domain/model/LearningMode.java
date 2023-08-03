package com.tekcapsule.marketing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LearningMode {
    SELF_PACED("Self Paced"),
    INSTRUCTOR_LEAD("Instructor Lead"),
    HYBRID("Hybrid");

    @Getter
    private String value;
}