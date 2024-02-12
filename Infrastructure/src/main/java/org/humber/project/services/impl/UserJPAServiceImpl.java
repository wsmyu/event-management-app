package org.humber.project.services.impl;

import org.humber.project.domain.User;
import org.humber.project.repositories.UserJPARepository;
import org.humber.project.services.UserJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJPAServiceImpl implements UserJPAService {
    private final UserJPARepository userJPARepository;

    @Autowired
    public UserJPAServiceImpl(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
    @Override
    public User saveUser(User user) {
        return null;

    }
}
