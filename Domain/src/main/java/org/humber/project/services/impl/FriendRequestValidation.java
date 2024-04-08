package org.humber.project.services.impl;

import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.FriendValidationException;
import org.humber.project.services.FriendJPAService;
import org.humber.project.services.FriendValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestValidation implements FriendValidationService {

    private final FriendJPAService friendJPAService;

    @Autowired
    public FriendRequestValidation(FriendJPAService friendJPAService) {
        this.friendJPAService = friendJPAService;
    }

    @Override
    public void validateFriendRequest(Long userId, Long friendUserId) {
        // Check if the user send request to himself
        if (userId.equals(friendUserId)) {
            throw new FriendValidationException(ErrorCode.INVALID_FRIEND_REQUEST);
        }

        // Check if the users are already friends
        if (friendJPAService.areFriends(userId, friendUserId)) {
            throw new FriendValidationException(ErrorCode.REPEATED_FRIEND_REQUEST);
        }
    }
}
