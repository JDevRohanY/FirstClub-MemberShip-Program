package com.JDevRohanY.FirstClubMembershipProgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CurrentSubscriptionResponseDto {
    String subscriptionId;
    String userId;
    // plan details
    String planId;
    String planType;
    double planPrice;
    // tier details
    String tierId;
    String tierType;
    double discountPercentage;
    boolean freeDelivery;
    boolean exclusiveDeals;
    boolean prioritySupport;
    // subscription details
    String status;
    Date startDate;
    Date expiryDate;
    long daysRemaining;
}
