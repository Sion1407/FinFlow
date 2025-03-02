package com.FinFlow.app.service;

import com.FinFlow.app.model.User;
import com.FinFlow.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Async
    public CompletableFuture<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return CompletableFuture.completedFuture(userRepository.save(user));
    }

    @Async
    public CompletableFuture<User> getUserByEmail(String email) {
        return CompletableFuture.completedFuture(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
