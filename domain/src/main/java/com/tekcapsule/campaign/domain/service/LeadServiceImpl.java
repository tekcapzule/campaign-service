package com.tekcapsule.campaign.domain.service;

import com.tekcapsule.campaign.domain.command.CreateLeadCommand;
import com.tekcapsule.campaign.domain.model.Lead;
import com.tekcapsule.campaign.domain.model.Status;
import com.tekcapsule.campaign.domain.repository.LeadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class LeadServiceImpl implements LeadService{

    private LeadRepository leadRepository;

    @Autowired
    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public void create(CreateLeadCommand createLeadCommand) {

        log.info(String.format("Entering create lead service - emailId :%s", createLeadCommand.getEmailId()));

        Lead lead = Lead.builder()
                .firstName(createLeadCommand.getFirstName())
                .lastName(createLeadCommand.getLastName())
                .emailId(createLeadCommand.getEmailId())
                .company(createLeadCommand.getCompany())
                .jobTitle(createLeadCommand.getJobTitle())
                .phoneNumber(createLeadCommand.getPhoneNumber())
                .country(createLeadCommand.getCountry())
                .comments(createLeadCommand.getComments())
                .status(Status.ACTIVE)
                .build();

        lead.setAddedOn(createLeadCommand.getExecOn());
        lead.setAddedBy(createLeadCommand.getExecBy().getUserId());
        leadRepository.save(lead);
    }


    @Override
    public List<Lead> findAll() {

        log.info("Entering findAll lead service");
        return leadRepository.findAll();
    }

}
