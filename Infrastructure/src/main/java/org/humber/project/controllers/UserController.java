package org.humber.project.controllers;

import org.humber.project.domain.User;
import org.humber.project.domain.UserLoginRequest;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully. User ID: " + createdUser.getUserId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user.");
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        // Check if the provided credentials match a user in the database
        User user = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        if (user != null) {
            // If login is successful, return the user details in the response
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByUsername(@RequestParam String username) {
        // Search for users based on the provided username
        List<User> users = userService.searchByUsername(username);

        if (!users.isEmpty()) {
            return ResponseEntity.ok().body(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
