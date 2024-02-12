package org.humber.project.services;

import org.humber.project.domain.User;

import java.util.List;

public interface UserJPAService{
    User getUserById(Long userId);


    List<User> getAllUsers();

    User saveUser(User user);


}
