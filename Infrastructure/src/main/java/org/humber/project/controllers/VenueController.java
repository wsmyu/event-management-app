package org.humber.project.controllers;

import org.humber.project.domain.Venue;
import org.humber.project.services.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<Venue> getVenueById(@PathVariable("venueId") Long venueId) {
        Venue venue = venueService.getVenueById(venueId);
        return ResponseEntity.ok(venue);
    }


}
