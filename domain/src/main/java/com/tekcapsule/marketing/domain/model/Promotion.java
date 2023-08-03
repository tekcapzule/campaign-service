package com.tekcapsule.marketing.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Promotion {
    private Boolean promoted;
    private String endsOnUtc;
    private String imageUrl;
    private String campaignUrl;
}
