package org.humber.project.services.impl;

import org.humber.project.domain.User;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserService;
import org.humber.project.services.UserRegistrationValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserJPAService userJPAService;
    private final UserRegistrationValidationService userRegistrationValidationService;

    @Autowired
    public UserServiceImpl(UserJPAService userJPAService, UserRegistrationValidationService userRegistrationValidationService) {
        this.userJPAService = userJPAService;
        this.userRegistrationValidationService = userRegistrationValidationService;
    }

    @Override
    public User createUser(User user){
        userRegistrationValidationService.validateUserRegistration(user);
        return userJPAService.createUser(user);
    }

    @Override
    public User loginUser(String username, String password) {
        User user = userJPAService.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public String generateJWTToken(User user) {
        // Generate a secure key with sufficient length for HS256 algorithm
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Generate JWT token with user ID and username as claims
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }


    @Override
    public List<User> searchByUsername(String username) {
        List<User> users = userJPAService.searchByUsername(username);
        return users;
    }
}
