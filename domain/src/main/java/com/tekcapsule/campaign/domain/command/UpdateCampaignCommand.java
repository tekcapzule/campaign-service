package com.tekcapsule.campaign.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.campaign.domain.model.Highlight;
import com.tekcapsule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UpdateCampaignCommand extends Command {
    private String campaignId;
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
