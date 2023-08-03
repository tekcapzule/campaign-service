package com.tekcapsule.marketing.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UpdateCommand extends Command {
    private String campaignId;
    private String title;
    private String subTitle;
    private String description;
    private String startsOn;
    private String endsOn;
    private String offer;
    private String resourceUrl;
    private String imageUrl;
}
