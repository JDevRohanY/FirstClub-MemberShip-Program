package com.JDevRohanY.FirstClubMembershipProgram.repositories;

import com.JDevRohanY.FirstClubMembershipProgram.models.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<String, User> userMap = new ConcurrentHashMap<>();
    private final Set<String> registeredEmails = ConcurrentHashMap.newKeySet();

    public User save(User user) {
        userMap.put(user.getId(), user);
        registeredEmails.add(user.getEmail());
        return user;
    }

    public boolean existsByEmail(String email) {
        return registeredEmails.contains(email);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }
}
