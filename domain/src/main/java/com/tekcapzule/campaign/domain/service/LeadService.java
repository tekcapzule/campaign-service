package com.tekcapzule.campaign.domain.service;

import com.tekcapzule.campaign.domain.command.CreateLeadCommand;
import com.tekcapzule.campaign.domain.model.Lead;

import java.util.List;

public interface LeadService {
    void create(CreateLeadCommand createLeadCommand);
    List<Lead> findAll();
}
