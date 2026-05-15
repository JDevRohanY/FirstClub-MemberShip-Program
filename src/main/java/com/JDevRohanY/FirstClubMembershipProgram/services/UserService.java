package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.models.User;

public interface UserService {
    User createUser(String name, String email);
}
