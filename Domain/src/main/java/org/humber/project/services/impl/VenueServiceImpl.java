package org.humber.project.services.impl;

import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.services.VenueJPAService;
import org.humber.project.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueJPAService venueJPAService;

    @Autowired
    public VenueServiceImpl(VenueJPAService venueJPAService) {
        this.venueJPAService = venueJPAService;
    }

    @Override
    public Venue getVenueById(Long venueId) {
        return venueJPAService.getVenueById(venueId);
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueJPAService.getAllVenues();
    }


}