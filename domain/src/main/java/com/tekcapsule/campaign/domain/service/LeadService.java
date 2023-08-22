package com.tekcapsule.campaign.domain.service;

import com.tekcapsule.campaign.domain.command.CreateLeadCommand;
import com.tekcapsule.campaign.domain.model.Lead;

import java.util.List;

public interface LeadService {
    void create(CreateLeadCommand createLeadCommand);
    List<Lead> findAll();
}
