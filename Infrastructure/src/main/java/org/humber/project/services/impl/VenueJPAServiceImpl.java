package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.repositories.VenueJPARepository;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventService;
import org.humber.project.services.VenueJPAService;
import org.humber.project.transformers.VenueEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Lazy
public class VenueJPAServiceImpl implements VenueJPAService {
    private final VenueJPARepository venueJPARepository;
    private final BookingService bookingService;
    private final EventService eventService;

    @Autowired
    public VenueJPAServiceImpl(VenueJPARepository venueJPARepository, @Lazy BookingService bookingService, @Lazy EventService eventService) {
        this.venueJPARepository = venueJPARepository;
        this.bookingService = bookingService;
        this.eventService = eventService;
    }


    @Override
    public Venue getVenueById(Long venueId) {
        return venueJPARepository.findById(venueId)
                .map(VenueEntityTransformer::transformToVenue)
                .orElse(null);
    }

    @Override
    public List<Venue> getAllVenues() {
        return Optional.of(venueJPARepository.findAll())
                .map(VenueEntityTransformer::transformToVenues)
                .orElse(null);
    }

    @Override
    public boolean isVenueAvailable(VenueBookingRequest venueBookingRequest) {

        // Retrieve existing bookings for the venue on the given date
        List<Booking> bookingsForDate = bookingService.retrieveVenueBookings(venueBookingRequest.getVenueId()).stream()
                .filter(booking -> {
                    Event event = eventService.retrieveEventDetails(booking.getEventId());
                    return event.getEventDate().equals(venueBookingRequest.getBookingDate());
                })
                .collect(Collectors.toList());

        // If there are no bookings for the venue on the given date, it's available for the whole day
        if (bookingsForDate.isEmpty()) {
            return true;
        }

        // Check if there are any conflicting bookings at the specified time
        LocalTime bookingStartTime = venueBookingRequest.getBookingStartTime();
        LocalTime bookingEndTime = venueBookingRequest.getBookingEndTime();

        boolean isAvailable = bookingsForDate.stream().noneMatch(booking -> {
            Event event = eventService.retrieveEventDetails(booking.getEventId());
            LocalTime existingBookingStartTime = booking.getBookingStartTime();
            LocalTime existingBookingEndTime = booking.getBookingEndTime();

            // Exclude the booking from the current event from the conflict check
            if (event.getEventId().equals(venueBookingRequest.getEventId())) {
                return false; // Skip this booking
            }

            return (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) ;
//
        });

        return isAvailable;
    }
}
