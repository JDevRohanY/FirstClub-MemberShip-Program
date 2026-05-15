package com.JDevRohanY.FirstClubMembershipProgram.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipPlan extends BaseModel{
    PlanType planType;
    double price;
    int durationInDays;
}
