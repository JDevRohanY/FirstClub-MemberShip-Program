package com.JDevRohanY.FirstClubMembershipProgram.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}