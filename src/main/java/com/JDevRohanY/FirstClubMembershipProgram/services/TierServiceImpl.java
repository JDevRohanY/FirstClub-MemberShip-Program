package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipTier;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.TierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TierServiceImpl implements TierService{
    private final TierRepository tierRepository;

    public TierServiceImpl(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    @Override
    public List<MembershipTier> getAllTiers() {
        log.info("Fetching all membership tiers");
        return tierRepository.getAllTiers();
    }
}
