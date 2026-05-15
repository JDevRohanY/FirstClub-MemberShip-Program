package com.JDevRohanY.FirstClubMembershipProgram.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MembershipTier extends BaseModel {
    TierType tierType;
    double discountPercentage;
    boolean freeDelivery;
    boolean exclusiveDeals;
    boolean prioritySupport;
    int minOrders;
    double minOrderValue;
}
