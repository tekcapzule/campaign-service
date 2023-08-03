package com.tekcapsule.campaign.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.campaign","com.tekcapsule.core"})
public class CampaignApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampaignApplication.class, args);
    }
}
