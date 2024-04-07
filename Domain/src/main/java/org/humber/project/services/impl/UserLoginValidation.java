package org.humber.project.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.humber.project.domain.User;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.UserValidationException;
import org.humber.project.services.UserJPAService;
import org.humber.project.services.UserLoginValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.ArrayList;

@Component
@Slf4j
public class UserLoginValidation implements UserLoginValidationService{
    private final UserJPAService userJPAService;
    @Autowired
    public UserLoginValidation(UserJPAService userJPAService) {
        this.userJPAService = userJPAService;
    }
    @Override
    public void validateUserLogin(@NonNull String username, @NonNull String password) {
        List<ErrorCode> errors = new ArrayList<>();

        if (!StringUtils.hasText(username)) {
            log.error("Username is null or empty");
            errors.add(ErrorCode.INVALID_USERNAME);
        }

        if (!StringUtils.hasText(password)) {
            log.error("Password is null or empty");
            errors.add(ErrorCode.INVALID_PASSWORD);
        }

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }
    }
}
