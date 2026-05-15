package com.JDevRohanY.FirstClubMembershipProgram.controllers;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipTier;
import com.JDevRohanY.FirstClubMembershipProgram.services.TierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiers")
public class TierController {
    private final TierService tierService;

    public TierController(TierService tierService) {
        this.tierService = tierService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipTier>> getAllTiers() {
        return ResponseEntity.ok(tierService.getAllTiers());
    }
}
