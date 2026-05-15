package com.JDevRohanY.FirstClubMembershipProgram.repositories;

import com.JDevRohanY.FirstClubMembershipProgram.exceptions.ActiveSubscriptionException;
import com.JDevRohanY.FirstClubMembershipProgram.models.Subscription;
import com.JDevRohanY.FirstClubMembershipProgram.models.SubscriptionStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class SubscriptionRepository {
    private final Map<String, Subscription> subscriptionMap = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public Subscription save(String userId, Subscription subscription) {
        lock.lock();
        try {
            // prevent duplicate active subscription
            boolean hasActive = subscriptionMap.values().stream()
                    .anyMatch(s -> s.getUserId().equals(userId)
                            && s.getStatus() == SubscriptionStatus.ACTIVE);
            if (hasActive) {
                throw new ActiveSubscriptionException(
                        "User already has an active subscription");
            }
            subscriptionMap.put(subscription.getId(), subscription);
            return subscription;
        } finally {
            lock.unlock();
        }
    }

    public Optional<Subscription> findById(String id) {
        return Optional.ofNullable(subscriptionMap.get(id));
    }

    public Optional<Subscription> findActiveByUserId(String userId) {
        return subscriptionMap.values().stream()
                .filter(s -> s.getUserId().equals(userId)
                        && s.getStatus() == SubscriptionStatus.ACTIVE)
                .findFirst();
    }
}
