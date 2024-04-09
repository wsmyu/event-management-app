package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.domain.Guest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.GuestValidationException;
import org.humber.project.services.EventService;
import org.humber.project.services.GuestService;
import org.humber.project.services.GuestValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestInvitationValidation implements GuestValidationService {

    private final EventService eventService;
    private final GuestService guestService;

    @Autowired
    public GuestInvitationValidation(EventService eventService, GuestService guestService) {
        this.eventService = eventService;
        this.guestService = guestService;
    }

    @Override
    public void validateGuestInvitation(Long userId, Guest guest) {
        if (guest.getUserId().equals(userId)) {
            throw new GuestValidationException(ErrorCode.CANNOT_INVITE_SELF);
        }

        // Check if the user is the creator of the event
        Event event = eventService.retrieveEventDetails(guest.getEventId());

        if (!event.getUserId().equals(userId)) {
            throw new GuestValidationException(ErrorCode.UNAUTHORIZED_GUEST_LIST_MANAGEMENT);
        }

        // Check if the user is already invited
        List<Guest> existingGuests = guestService.getGuestListByEventId(guest.getEventId());

        boolean isAlreadyInvited = existingGuests.stream()
                .anyMatch(existingGuest -> existingGuest.getUserId().equals(guest.getUserId()));

        if (isAlreadyInvited) {
            throw new GuestValidationException(ErrorCode.GUEST_ALREADY_INVITED);
        }
    }
}