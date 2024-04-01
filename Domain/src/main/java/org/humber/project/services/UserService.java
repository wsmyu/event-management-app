package org.humber.project.services;

import org.humber.project.domain.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User loginUser(String username, String password);
    String generateJWTToken(User user); // Add generateJWTToken method
    List<User> searchByUsername(String username);
}
