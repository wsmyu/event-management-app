package org.humber.project.services;

import org.humber.project.domain.Venue;

import java.time.LocalDate;
import java.time.LocalTime;

public interface VenueService {
    Venue bookVenue(Long venueId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime);
}
