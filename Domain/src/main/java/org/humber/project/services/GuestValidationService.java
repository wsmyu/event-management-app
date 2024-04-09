package org.humber.project.services;

import org.humber.project.domain.Guest;

public interface GuestValidationService {
    void validateGuestInvitation(Long userId, Guest guest);
}
