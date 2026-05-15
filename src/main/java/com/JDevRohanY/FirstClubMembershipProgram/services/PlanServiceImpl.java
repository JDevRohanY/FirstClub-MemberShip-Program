package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipPlan;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<MembershipPlan> getAllPlans() {
        log.info("Fetching all membership plans");
        return planRepository.getAllPlans();
    }
}
