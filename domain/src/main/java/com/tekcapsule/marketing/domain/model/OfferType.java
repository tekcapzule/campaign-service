package com.tekcapsule.marketing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OfferType {
    NEW_USER("New User"),
    ANY_USER("Any User");
    @Getter
    private String value;
}