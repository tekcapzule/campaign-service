package com.tekcapsule.marketing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeliveryMode {
    ONLINE("Online"),
    IN_CLASSROOM("In Classroom"),
    HYBRID("Hybrid");

    @Getter
    private String value;
}