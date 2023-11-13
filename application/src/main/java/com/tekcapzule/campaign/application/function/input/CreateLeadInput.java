package com.tekcapzule.campaign.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

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