package org.humber.project.services;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;

import java.util.List;
import java.util.Optional;

public interface VenueJPAService {

//    Booking bookVenue(VenueBookingRequest bookingRequest);
    Venue getVenueById(Long venueId);
    List<Venue> getAllVenues();
    boolean isVenueAvailable(VenueBookingRequest venueBookingRequest);
//    Venue updateVenue(Long id, Venue updatedVenue);

}
