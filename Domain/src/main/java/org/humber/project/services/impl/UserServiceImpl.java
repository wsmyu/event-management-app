package org.humber.project.services.impl;

import org.humber.project.domain.User;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserJPAService userJPAService;

    @Autowired
    public UserServiceImpl(UserJPAService userJPAService) {
        this.userJPAService = userJPAService;
    }

    @Override
    public User createUser(User user){
        return userJPAService.createUser(user);
    }

    @Override
    public User loginUser(String username, String password) {
        // Implement login logic, e.g., check password
        User user = userJPAService.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Passwords match, return the user
            return user;
        } else {
            // Invalid username or password
            return null;
        }
    }

    @Override
    public List<User> searchByUsername(String username) {
        List<User> users = userJPAService.searchByUsername(username);

        return users;
    }
}
