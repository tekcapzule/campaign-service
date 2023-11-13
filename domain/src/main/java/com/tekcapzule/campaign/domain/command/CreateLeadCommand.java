package com.tekcapzule.campaign.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateLeadCommand extends Command {
    private String firstName;
    private String lastName;
    private String emailId;
    private String company;
    private String jobTitle;
    private String phoneNumber;
    private String country;
    private String comments;
}

