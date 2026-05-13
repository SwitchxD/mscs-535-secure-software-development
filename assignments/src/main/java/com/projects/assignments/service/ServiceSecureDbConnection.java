package com.projects.assignments.service;

import com.projects.assignments.entity.User;
import com.projects.assignments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceSecureDbConnection {

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticates a user by username and password.
     */
    public User authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.orElse(null);
    }

    /**
     * Retrieves a user by their primary key ID.
     */
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
