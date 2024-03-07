package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.services.VenueJPAService;
import org.humber.project.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueJPAService venueJPAService;

    @Autowired
    public VenueServiceImpl(VenueJPAService venueJPAService) {
        this.venueJPAService = venueJPAService;
    }

    @Override
    public Booking bookVenue(VenueBookingRequest bookingRequest) {
        //Add user validation later
        return venueJPAService.bookVenue(bookingRequest);
    }

    @Override
    public Venue getVenue(Long venueId){
        return venueJPAService.getVenueById(venueId);
    }

    @Override
    public List<Venue> getAllVenues(){
        return venueJPAService.getAllVenues();
    }

    @Override
    public boolean checkVenueAvailability(VenueBookingRequest venueBookingRequest){
        return venueJPAService.isVenueAvailable(venueBookingRequest);
    }
}