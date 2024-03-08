package org.humber.project.services;

import org.humber.project.domain.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User loginUser(String username, String password);
    List<User> searchByUsername(String username);
}
