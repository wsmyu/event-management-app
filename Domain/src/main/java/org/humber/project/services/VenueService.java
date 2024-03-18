package org.humber.project.services;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VenueService {
    Venue getVenueById(Long venueId);
    List<Venue> getAllVenues();

}
