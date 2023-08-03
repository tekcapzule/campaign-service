package com.tekcapsule.marketing.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.market","com.tekcapsule.core"})
public class MarketingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
    }
}
