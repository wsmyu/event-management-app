package org.humber.project.services;

import org.humber.project.domain.User;

public interface UserRegistrationValidationService {
    void validateUserRegistration(User user);
}
