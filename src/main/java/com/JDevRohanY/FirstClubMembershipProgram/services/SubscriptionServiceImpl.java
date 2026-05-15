package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.exceptions.PlanNotFoundException;
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
        return null;
    }

    @Override
    public Subscription cancelSubscription(String subscriptionId) {
        return null;
    }

    @Override
    public Subscription getCurrentSubscription(String userId) {
        return null;
    }
}
