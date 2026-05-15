package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.exceptions.PlanNotFoundException;
import com.JDevRohanY.FirstClubMembershipProgram.exceptions.SubscriptionNotFoundException;
import com.JDevRohanY.FirstClubMembershipProgram.exceptions.TierNotFoundException;
import com.JDevRohanY.FirstClubMembershipProgram.exceptions.UserNotFoundException;
import com.JDevRohanY.FirstClubMembershipProgram.models.*;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.PlanRepository;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.SubscriptionRepository;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.TierRepository;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TierRepository tierRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository, PlanRepository planRepository, TierRepository tierRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
    }

    @Override
    public Subscription createSubscription(String userId, String planId, String tierId) {
        log.info("Creating subscription for userId: {}", userId);

        // validate user exists
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        // validate plan exists
        MembershipPlan plan = planRepository.findById(planId).orElseThrow(() -> new PlanNotFoundException("Plan not found: " + planId));

        // validate tier exists
        MembershipTier tier = tierRepository.findById(tierId).orElseThrow(() -> new TierNotFoundException("Tier not found: " + tierId));

        // calculate expiry date
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, plan.getDurationInDays());
        Date expiryDate = calendar.getTime();

        // build subscription
        Subscription subscription = Subscription.builder().userId(userId).planId(planId).tierId(tierId).status(SubscriptionStatus.ACTIVE).startDate(startDate).expiryDate(expiryDate).build();
        subscription.setId(UUID.randomUUID().toString());
        subscription.setCreatedAt(new Date());
        subscription.setUpdatedAt(new Date());

        // update user membership status
        user.setMembershipStatus(MembershipStatus.ACTIVE);
        user.setUpdatedAt(new Date());

        subscriptionRepository.save(userId, subscription);
        log.info("Subscription created: {}", subscription.getId());
        return subscription;
    }

    @Override
    public Subscription upgradeTier(String subscriptionId, String newTierId) {
        log.info("Updating tier for subscriptionId: {}", subscriptionId);

        // validate subscription exists
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found: " + subscriptionId));

        // validate subscription is active
        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalArgumentException("Cannot update tier — subscription is not active");
        }

        // validate new tier exists
        MembershipTier newTier = tierRepository.findById(newTierId).orElseThrow(() -> new TierNotFoundException("Tier not found: " + newTierId));

        // validate not same tier
        if (subscription.getTierId().equals(newTierId)) {
            throw new IllegalArgumentException("Already on this tier: " + newTierId);
        }

        // update tier
        subscription.setTierId(newTierId);
        subscription.setUpdatedAt(new Date());

        log.info("Tier updated to: {} for subscription: {}", newTierId, subscriptionId);
        return subscription;
    }

    @Override
    public Subscription cancelSubscription(String subscriptionId) {
        log.info("Cancelling subscription: {}", subscriptionId);

        // validate subscription exists
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found: " + subscriptionId));

        // validate it's active
        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalArgumentException("Subscription is already " + subscription.getStatus());
        }

        // cancel subscription
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setUpdatedAt(new Date());

        // update user membership status to inactive
        User user = userRepository.findById(subscription.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found: " + subscription.getUserId()));
        user.setMembershipStatus(MembershipStatus.INACTIVE);
        user.setUpdatedAt(new Date());

        log.info("Subscription cancelled: {}", subscriptionId);
        return subscription;
    }

    @Override
    public Subscription getCurrentSubscription(String userId) {
        return null;
    }
}
