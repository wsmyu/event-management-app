package org.humber.project.services.impl;

import org.humber.project.domain.User;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserJPAService userJPAService;

    @Autowired
    public UserServiceImpl(UserJPAService userJPAService) {
        this.userJPAService = userJPAService;
    }

    @Override
    public void registerUser(User user){
        userJPAService.saveUser(user);
    }

    @Override
    public User loginUser(String username, String password) {
        return null;
        // Implement login logic, e.g., check password
    }
}
