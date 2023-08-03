package com.tekcapsule.campaign.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String title;
    private String subTitle;
    private String description;
    private String startsOn;
    private String endsOn;
    private String offer;
    private String resourceUrl;
    private String imageUrl;
}