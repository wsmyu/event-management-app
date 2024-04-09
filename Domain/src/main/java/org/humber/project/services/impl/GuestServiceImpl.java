package org.humber.project.services.impl;

import org.humber.project.domain.Guest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.GuestValidationException;
import org.humber.project.services.GuestJPAService;
import org.humber.project.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestJPAService guestJPAService;

    @Autowired
    public GuestServiceImpl(GuestJPAService guestJPAService) {
        this.guestJPAService = guestJPAService;
    }

    @Override
    public Guest manageGuest(Guest guest) {

        if (guest == null || guest.getEventId() == null) {
            throw new GuestValidationException(ErrorCode.INVALID_GUEST_DETAILS); // Assuming INVALID_GUEST_DETAILS is defined in ErrorCode
        }
        // Directly delegate the call to the JPA service layer
        return guestJPAService.manageGuest(guest);

    }
    @Override
    public List<Guest> getGuestListByEventId(Long eventId) {
        if (eventId == null) {
            throw new GuestValidationException(ErrorCode.INVALID_EVENT_ID);
        }
        return guestJPAService.getGuestListByEventId(eventId);
        // No exception is thrown if the guest list is empty, allowing for validation logic to handle this scenario
    }

//    @Override
//    public List<Guest> getGuestListByEventId(Long eventId) {
//        if (eventId == null) {
//            throw new GuestValidationException(ErrorCode.INVALID_EVENT_ID);
//        }
//        List<Guest> guests = guestJPAService.getGuestListByEventId(eventId);
//        if (guests.isEmpty()) {
//            throw new GuestValidationException(ErrorCode.GUEST_NOT_FOUND);
//        }
//        return guests;
//    }

    @Override
    public Guest updateGuestStatus(Long guestId, String status) {
        // Validation logic here
        return guestJPAService.updateGuestStatus(guestId, status);
    }

    @Override
    public List<Guest> getInvitationsByUserId(Long userId) {
        return guestJPAService.getInvitationsByUserId(userId);
    }

    @Override
    public List<Long> getAcceptedEventIdsByUserId(Long userId) {
        return guestJPAService.getAcceptedEventIdsByUserId(userId);
    }


}
