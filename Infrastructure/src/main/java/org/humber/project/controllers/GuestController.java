package org.humber.project.controllers;

import org.humber.project.domain.Guest;
import org.humber.project.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping("/manage")
    public ResponseEntity<Guest> manageGuest(@RequestBody Guest guest) {
        Guest managedGuest = guestService.manageGuest(guest);
        return new ResponseEntity<>(managedGuest, HttpStatus.CREATED);
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
