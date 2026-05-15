package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;
import com.JDevRohanY.FirstClubMembershipProgram.stratergy.TierEligibilityStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierEligibiltyService {
    private final List<TierEligibilityStrategy> strategies;

    // Spring injects all implementations automatically
    public TierEligibiltyService(List<TierEligibilityStrategy> strategies) {
        this.strategies = strategies;
    }

    // returns highest tier user is eligible for
    public TierType getEligibleTier(String userId) {
        // check PLATINUM first, then GOLD, then SILVER
        if (isEligibleFor(userId, TierType.PLATINUM)) return TierType.PLATINUM;
        if (isEligibleFor(userId, TierType.GOLD)) return TierType.GOLD;
        if (isEligibleFor(userId, TierType.SILVER)) return TierType.SILVER;
        return null; // not eligible for any tier
    }

    private boolean isEligibleFor(String userId, TierType tierType) {
        return strategies.stream()
                .filter(s -> s.getTierType() == tierType)
                .anyMatch(s -> s.isEligible(userId));
    }
}
