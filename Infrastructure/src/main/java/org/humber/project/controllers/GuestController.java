package org.humber.project.controllers;

import org.humber.project.domain.Guest;
import org.humber.project.exceptions.GuestValidationException;
import org.humber.project.services.GuestService;
import org.humber.project.services.GuestValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;
    private final GuestValidationService guestValidationService;

    @Autowired
    public GuestController(GuestService guestService, GuestValidationService guestValidationService) {
        this.guestService = guestService;
        this.guestValidationService = guestValidationService;

    }

    @PostMapping("/{userId}/manage")
    public ResponseEntity<?> manageGuest(@PathVariable Long userId, @RequestBody Guest guest) {
        try {
            // Validate the guest invitation
            guestValidationService.validateGuestInvitation(userId, guest);

            // Proceed with managing the guest invitation
            Guest managedGuest = guestService.manageGuest(guest);
            return new ResponseEntity<>(managedGuest, HttpStatus.CREATED);
        } catch (GuestValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorCode().getMessage());
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<Guest>> getGuestsByEventId(@PathVariable Long eventId) {
        List<Guest> guests = guestService.getGuestListByEventId(eventId);
        return ResponseEntity.ok(guests);
    }

    @PatchMapping("/{guestId}/status")
    public ResponseEntity<Guest> updateGuestStatus(@PathVariable Long guestId, @RequestBody Map<String, String> statusUpdate) {
        Guest updatedGuest = guestService.updateGuestStatus(guestId, statusUpdate.get("status"));
        return ResponseEntity.ok(updatedGuest);
    }

    @GetMapping("/user/{userId}/invitations")
    public ResponseEntity<List<Guest>> getInvitationsByUserId(@PathVariable Long userId) {
        List<Guest> invitations = guestService.getInvitationsByUserId(userId);
        return ResponseEntity.ok(invitations);
    }

}
