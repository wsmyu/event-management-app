package org.humber.project.services;

import org.humber.project.domain.User;

public interface UserService {
    void registerUser(User user);
    User loginUser(String username, String password);

}
