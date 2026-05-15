package com.JDevRohanY.FirstClubMembershipProgram.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Subscription extends BaseModel {
    String userId;
    String planId;
    String tierId;
    SubscriptionStatus status;
    Date startDate;
    Date expiryDate;
}
