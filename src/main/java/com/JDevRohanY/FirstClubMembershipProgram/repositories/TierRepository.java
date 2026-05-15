package com.JDevRohanY.FirstClubMembershipProgram.repositories;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipTier;
import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TierRepository {
    private final Map<String, MembershipTier> tierMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void seedTiers() {
        addTier("tier-silver", TierType.SILVER, 5.0, false, false, false, 5, 1000.0);
        addTier("tier-gold", TierType.GOLD, 10.0, true, false, false, 15, 5000.0);
        addTier("tier-platinum", TierType.PLATINUM, 20.0, true, true, true, 30, 10000.0);
    }

    private void addTier(String id, TierType type, double discount,
                         boolean freeDelivery, boolean exclusiveDeals,
                         boolean prioritySupport, int minOrders, double minOrderValue) {
        MembershipTier tier = MembershipTier.builder()
                .tierType(type)
                .discountPercentage(discount)
                .freeDelivery(freeDelivery)
                .exclusiveDeals(exclusiveDeals)
                .prioritySupport(prioritySupport)
                .minOrders(minOrders)
                .minOrderValue(minOrderValue)
                .build();
        tier.setId("tier-silver");
        tier.setCreatedAt(new Date());
        tierMap.put(id, tier);
    }

    public List<MembershipTier> getAllTiers() {
        return new ArrayList<>(tierMap.values());
    }

    public Optional<MembershipTier> findById(String id) {
        return Optional.ofNullable(tierMap.get(id));
    }
}
