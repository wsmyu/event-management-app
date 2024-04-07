package org.humber.project.services;


public interface FriendValidationService {
    void validateFriendRequest(Long userId, Long friendUserId);
}
