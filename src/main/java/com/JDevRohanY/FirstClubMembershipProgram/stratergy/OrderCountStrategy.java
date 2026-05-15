package com.JDevRohanY.FirstClubMembershipProgram.stratergy;

import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderCountStrategy implements TierEligibilityStrategy{
    private final int minOrders;
    private final TierType tierType;

    // simulate order count — in real system this calls order service
    private final Map<String, Integer> userOrderCount = new ConcurrentHashMap<>();

    public OrderCountStrategy(int minOrders, TierType tierType) {
        this.minOrders = minOrders;
        this.tierType = tierType;
    }

    @Override
    public boolean isEligible(String userId) {
        int orderCount = userOrderCount.getOrDefault(userId, 0);
        return orderCount >= minOrders;
    }

    @Override
    public TierType getTierType() {
        return tierType;
    }

    // call this when user places an order
    public void incrementOrderCount(String userId) {
        userOrderCount.merge(userId, 1, Integer::sum);
    }
}
