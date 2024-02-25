package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.entities.VenueEntity;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.exceptions.VenueNotFoundException;
import org.humber.project.repositories.VenueJPARepository;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventService;
import org.humber.project.services.VenueJPAService;
import org.humber.project.transformers.EventEntityTransformer;
import org.humber.project.transformers.VenueEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueJPAServiceImpl implements VenueJPAService {
    private final VenueJPARepository venueJPARepository;
    private final BookingService bookingService;
    private final EventService eventService;

    @Autowired
    public VenueJPAServiceImpl(VenueJPARepository venueJPARepository, BookingService bookingService, EventService eventService) {
        this.venueJPARepository = venueJPARepository;
        this.bookingService = bookingService;
        this.eventService = eventService;
    }

    @Override
    public Booking bookVenue(VenueBookingRequest bookingRequest) {

        // Check venue availability
        VenueEntity venue = venueJPARepository.findById(bookingRequest.getVenueId())
                .orElseThrow(() -> new VenueNotFoundException("Venue not found"));

//         Check if the venue is available for the requested date and time
        if (!isVenueAvailable(venue.getVenueId(), bookingRequest.getBookingDate(), bookingRequest.getBookingTime())) {
            throw new VenueNotAvailableException("Venue is not available at the requested date and time");
        }

        // Create a new Booking entity with the booking details
        Booking booking = new Booking();
        booking.setVenueId(venue.getVenueId());
        booking.setUserId(bookingRequest.getUserId());
        booking.setEventId(bookingRequest.getEventId());
        booking.setBookingCreationDate(LocalDate.now());


        // Create the booking entity and save to the database
        return bookingService.createBooking(booking);

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

    private boolean isVenueAvailable(Long venueId, LocalDate bookingDate, LocalTime bookingTime) {
        // Retrieve existing bookings for the venue on the given date
        List<Booking> bookingsForDate = bookingService.retrieveVenueBookings(venueId).stream()
                .filter(booking -> {
                    Event event = eventService.retrieveEventDetails(booking.getEventId());
                    return event.getEventDate().equals(bookingDate);
                })
                .collect(Collectors.toList());

        // If there are no bookings for the venue on the given date, it's available for the whole day
        if (bookingsForDate.isEmpty()) {
            return true;
        }

        // Check if there are any conflicting bookings at the specified time
        boolean isAvailable = bookingsForDate.stream()
                .noneMatch(booking -> {
                    Event event = eventService.retrieveEventDetails(booking.getEventId());
                    return event.getEventTime().equals(bookingTime);
                });

        return isAvailable;
    }

}
