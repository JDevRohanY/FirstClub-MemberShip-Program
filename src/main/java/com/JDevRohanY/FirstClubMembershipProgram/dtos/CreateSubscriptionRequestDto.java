package com.JDevRohanY.FirstClubMembershipProgram.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionRequestDto {
    String userId;
    String planId;
    String tierId;
}
