package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.dtos.CurrentSubscriptionResponseDto;
import com.JDevRohanY.FirstClubMembershipProgram.models.Subscription;

public interface SubscriptionService {
    Subscription createSubscription(String userId, String planId, String tierId);

    Subscription upgradeTier(String subscriptionId, String newTierId);

    Subscription cancelSubscription(String subscriptionId);

    CurrentSubscriptionResponseDto getCurrentSubscription(String userId);
}
