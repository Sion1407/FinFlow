package com.FinFlow.app.controller;

import com.FinFlow.app.model.User;
import com.FinFlow.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public CompletableFuture<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{email}")
    public CompletableFuture<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
