package com.tekcapsule.marketing.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.marketing.domain.model.*;
import com.tekcapsule.marketing.domain.model.Module;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
    private String title;
    private String topicCode;
    private String author;
    private String publisher;
    private String duration;
    private String resourceUrl;
    private String summary;
    private String description;
    private List<Module> modules;
    private PrizingModel prizingModel;
    private DeliveryMode deliveryMode;
    private LearningMode learningMode;
    private String imageUrl;

    private int recommendations;

    private Promotion promotion;

}