package com.tekcapzule.campaign.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.campaign.domain.model.Highlight;
import com.tekcapzule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCampaignCommand extends Command {
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