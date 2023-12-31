package com.tekcapzule.campaign.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.campaign.domain.model.Highlight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateCampaignInput {
    private String title;
    private String subTitle;
    private String description;
    private List<Highlight> highlights;
    private String startsOn;
    private String endsOn;
    private String offer;
    private String resourceUrl;
    private String imageUrl;
}