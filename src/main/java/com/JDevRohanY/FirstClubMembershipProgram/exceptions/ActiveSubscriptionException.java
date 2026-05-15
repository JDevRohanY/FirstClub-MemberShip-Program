package com.JDevRohanY.FirstClubMembershipProgram.exceptions;

public class ActiveSubscriptionException extends RuntimeException {
    public ActiveSubscriptionException(String message) {
        super(message);
    }
}