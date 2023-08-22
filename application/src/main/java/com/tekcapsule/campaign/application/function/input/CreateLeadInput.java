package com.tekcapsule.campaign.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.campaign.domain.model.Highlight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateLeadInput {
    private String firstName;
    private String lastName;
    private String emailId;
    private String company;
    private String jobTitle;
    private String phoneNumber;
    private String country;
    private String comments;
}