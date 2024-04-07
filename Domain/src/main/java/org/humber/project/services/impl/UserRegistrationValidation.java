package org.humber.project.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.humber.project.domain.User;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.UserValidationException;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserRegistrationValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.ArrayList;


@Component
@Slf4j
public class UserRegistrationValidation implements UserRegistrationValidationService {
    private final UserJPAService userJPAService;

    @Autowired
    public UserRegistrationValidation(UserJPAService userJPAService) {
        this.userJPAService = userJPAService;
    }
    @Override
    public void validateUserRegistration(@NonNull User user) {
        List<ErrorCode> errors = new ArrayList<>();

        // Check if the username is null or empty
        if (!StringUtils.hasText(user.getUsername())) {
            log.error("Username is null or empty");
            errors.add(ErrorCode.INVALID_USERNAME);
        }

        // Check if the password is null or empty
        if (!StringUtils.hasText(user.getPassword())) {
            log.error("Password is null or empty");
            errors.add(ErrorCode.INVALID_PASSWORD);
        }

        // Check if the firstName is null or empty
        if (!StringUtils.hasText(user.getFirstName())) {
            log.error("First Name is null or empty");
            errors.add(ErrorCode.INVALID_FIRST_NAME);
        }

        // Check if the lastName is null or empty
        if (!StringUtils.hasText(user.getLastName())) {
            log.error("Last Name is null or empty");
            errors.add(ErrorCode.INVALID_LAST_NAME);
        }

        // Check if the email is null or empty
        if (!StringUtils.hasText(user.getEmail())) {
            log.error("Email is null or empty");
            errors.add(ErrorCode.INVALID_EMAIL);
        }

        // Check if the username is already registered
        if (userJPAService.getUserByUsername(user.getUsername()) != null) {
            log.error("Username '{}' already exists", user.getUsername());
            errors.add(ErrorCode.USERNAME_ALREADY_REGISTERED);
        }

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }

        // Check if the email is already registered
//        if (userJPAService.getUserByEmail(user.getEmail()) != null) {
//            log.error("Email '{}' already exists", user.getEmail());
//            throw new UserValidationException(ErrorCode.EMAIL_ALREADY_REGISTERED);
//        }
    }
}
