package com.JDevRohanY.FirstClubMembershipProgram.services;

import com.JDevRohanY.FirstClubMembershipProgram.models.MembershipStatus;
import com.JDevRohanY.FirstClubMembershipProgram.models.User;
import com.JDevRohanY.FirstClubMembershipProgram.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email) {
        log.info("Creating user with email: {}", email);

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setMembershipStatus(MembershipStatus.INACTIVE);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userRepository.save(user);
        log.info("User created with id: {}", user.getId());
        return user;
    }
}
