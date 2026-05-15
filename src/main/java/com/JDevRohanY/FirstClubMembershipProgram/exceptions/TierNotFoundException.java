package com.JDevRohanY.FirstClubMembershipProgram.exceptions;

public class TierNotFoundException extends RuntimeException {
    public TierNotFoundException(String message) {
        super(message);
    }
}
