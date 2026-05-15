package com.JDevRohanY.FirstClubMembershipProgram.controllers;

import com.JDevRohanY.FirstClubMembershipProgram.dtos.CreateSubscriptionRequestDto;
import com.JDevRohanY.FirstClubMembershipProgram.dtos.CreateSubscriptionResponseDto;
import com.JDevRohanY.FirstClubMembershipProgram.dtos.UpdateTierRequestDto;
import com.JDevRohanY.FirstClubMembershipProgram.models.Subscription;
import com.JDevRohanY.FirstClubMembershipProgram.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<CreateSubscriptionResponseDto> createSubscription(@RequestBody CreateSubscriptionRequestDto requestDto) {

        Subscription subscription = subscriptionService.createSubscription(requestDto.getUserId(), requestDto.getPlanId(), requestDto.getTierId());

        CreateSubscriptionResponseDto responseDto = new CreateSubscriptionResponseDto();
        responseDto.setId(subscription.getId());
        responseDto.setUserId(subscription.getUserId());
        responseDto.setPlanId(subscription.getPlanId());
        responseDto.setTierId(subscription.getTierId());
        responseDto.setStatus(subscription.getStatus().toString());
        responseDto.setStartDate(subscription.getStartDate());
        responseDto.setExpiryDate(subscription.getExpiryDate());
        responseDto.setCreatedAt(subscription.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PatchMapping("/{id}/tier")
    public ResponseEntity<CreateSubscriptionResponseDto> updateTier(@PathVariable String id, @RequestBody UpdateTierRequestDto requestDto) {

        Subscription subscription = subscriptionService.upgradeTier(id, requestDto.getTierId());

        CreateSubscriptionResponseDto responseDto = new CreateSubscriptionResponseDto();
        responseDto.setId(subscription.getId());
        responseDto.setUserId(subscription.getUserId());
        responseDto.setPlanId(subscription.getPlanId());
        responseDto.setTierId(subscription.getTierId());
        responseDto.setStatus(subscription.getStatus().toString());
        responseDto.setStartDate(subscription.getStartDate());
        responseDto.setExpiryDate(subscription.getExpiryDate());
        responseDto.setCreatedAt(subscription.getCreatedAt());

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CreateSubscriptionResponseDto> cancelSubscription(@PathVariable String id) {

        Subscription subscription = subscriptionService.cancelSubscription(id);

        CreateSubscriptionResponseDto responseDto = new CreateSubscriptionResponseDto();
        responseDto.setId(subscription.getId());
        responseDto.setUserId(subscription.getUserId());
        responseDto.setPlanId(subscription.getPlanId());
        responseDto.setTierId(subscription.getTierId());
        responseDto.setStatus(subscription.getStatus().toString());
        responseDto.setStartDate(subscription.getStartDate());
        responseDto.setExpiryDate(subscription.getExpiryDate());
        responseDto.setCreatedAt(subscription.getCreatedAt());

        return ResponseEntity.ok(responseDto);
    }
}
