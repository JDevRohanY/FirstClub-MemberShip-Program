package com.JDevRohanY.FirstClubMembershipProgram.stratergy;

import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;

public interface TierEligibilityStrategy {
    boolean isEligible(String userId);
    TierType getTierType();
}
