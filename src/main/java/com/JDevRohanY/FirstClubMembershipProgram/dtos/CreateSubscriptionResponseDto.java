package com.JDevRohanY.FirstClubMembershipProgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateSubscriptionResponseDto {
    String id;
    String userId;
    String planId;
    String tierId;
    String status;
    Date startDate;
    Date expiryDate;
    Date createdAt;
}
