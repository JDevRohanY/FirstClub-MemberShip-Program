package com.JDevRohanY.FirstClubMembershipProgram.stratergy;

import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderValueStrategy implements TierEligibilityStrategy {
    private final double minOrderValue;
    private final TierType tierType;

    // simulate monthly order value
    private final Map<String, Double> userOrderValue = new ConcurrentHashMap<>();

    public OrderValueStrategy(double minOrderValue, TierType tierType) {
        this.minOrderValue = minOrderValue;
        this.tierType = tierType;
    }

    @Override
    public boolean isEligible(String userId) {
        double totalValue = userOrderValue.getOrDefault(userId, 0.0);
        return totalValue >= minOrderValue;
    }

    @Override
    public TierType getTierType() {
        return tierType;
    }

    public void addOrderValue(String userId, double value) {
        userOrderValue.merge(userId, value, Double::sum);
    }
}
