package org.humber.project.controllers;

import org.humber.project.domain.User;
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
}
