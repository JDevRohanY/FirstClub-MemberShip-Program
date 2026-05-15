package com.JDevRohanY.FirstClubMembershipProgram.repositories;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipPlan;
import com.JDevRohanY.FirstClubMembershipProgram.models.PlanType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PlanRepository {
    private final Map<String, MembershipPlan> planMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void seedPlans() {
        addPlan("plan-monthly", PlanType.MONTHLY, 99.00, 30);
        addPlan("plan-quarterly", PlanType.QUARTERLY, 249.0, 90);
        addPlan("plan-yearly", PlanType.YEARLY, 899.0, 365);
    }

    private void addPlan(String id, PlanType type, double price, int days) {
        MembershipPlan plan = new MembershipPlan();
        plan.setId(id);
        plan.setPlanType(type);
        plan.setPrice(price);
        plan.setDurationInDays(days);
        plan.setCreatedAt(new Date());
        planMap.put(id, plan);
    }

    public List<MembershipPlan> getAllPlans() {
        return new ArrayList<>(planMap.values());
    }

    public Optional<MembershipPlan> findById(String id) {
        return Optional.ofNullable(planMap.get(id));
    }

}
