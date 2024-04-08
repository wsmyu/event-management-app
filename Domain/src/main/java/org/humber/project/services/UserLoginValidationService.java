package org.humber.project.services;

import org.humber.project.domain.User;

public interface UserLoginValidationService {
    void validateUserLogin(String username, String password);

}
