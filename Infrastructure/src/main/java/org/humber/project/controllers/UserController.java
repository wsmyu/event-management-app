package org.humber.project.controllers;

import org.humber.project.domain.User;
import org.humber.project.domain.UserLoginRequest;
import org.humber.project.services.UserService;
import org.humber.project.services.UserJPAService;
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

    @Autowired
    private UserJPAService userJPAService;

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
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        // Check if the provided credentials match a user in the database
        User user = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        if (user != null) {
            // If login is successful, generate JWT token
            String token = userService.generateJWTToken(user); // Generate JWT token using UserService method

            // Return the JWT token in the response
            return ResponseEntity.ok().body(token);
        } else {
            // If login failed, return unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userJPAService.getUserById(userId);
        if (user != null) {
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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