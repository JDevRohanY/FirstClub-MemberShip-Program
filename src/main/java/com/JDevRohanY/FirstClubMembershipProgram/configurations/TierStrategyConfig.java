package com.JDevRohanY.FirstClubMembershipProgram.configurations;

import com.JDevRohanY.FirstClubMembershipProgram.models.TierType;
import com.JDevRohanY.FirstClubMembershipProgram.stratergy.OrderCountStrategy;
import com.JDevRohanY.FirstClubMembershipProgram.stratergy.OrderValueStrategy;
import com.JDevRohanY.FirstClubMembershipProgram.stratergy.TierEligibilityStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TierStrategyConfig {

    @Bean
    public TierEligibilityStrategy silverOrderCountStrategy() {
        return new OrderCountStrategy(5, TierType.SILVER);
    }

    @Bean
    public TierEligibilityStrategy goldOrderCountStrategy() {
        return new OrderCountStrategy(15, TierType.GOLD);
    }

    @Bean
    public TierEligibilityStrategy platinumOrderCountStrategy() {
        return new OrderCountStrategy(30, TierType.PLATINUM);
    }

    @Bean
    public TierEligibilityStrategy silverOrderValueStrategy() {
        return new OrderValueStrategy(1000.0, TierType.SILVER);
    }

    @Bean
    public TierEligibilityStrategy goldOrderValueStrategy() {
        return new OrderValueStrategy(5000.0, TierType.GOLD);
    }

    @Bean
    public TierEligibilityStrategy platinumOrderValueStrategy() {
        return new OrderValueStrategy(10000.0, TierType.PLATINUM);
    }
}
