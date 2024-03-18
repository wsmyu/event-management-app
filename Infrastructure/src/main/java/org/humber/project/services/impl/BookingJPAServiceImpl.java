package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.entities.BookingEntity;
import org.humber.project.exceptions.BookingNotFoundException;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.repositories.BookingJPARepository;
import org.humber.project.services.BookingJPAService;
import org.humber.project.transformers.BookingEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.humber.project.transformers.BookingEntityTransformer.transformToBooking;
import static org.humber.project.transformers.BookingEntityTransformer.transformToBookingEntity;

@Service
public class BookingJPAServiceImpl implements BookingJPAService {
    private final BookingJPARepository bookingJPARepository;

    @Autowired
    public BookingJPAServiceImpl(BookingJPARepository bookingJPARepository) {
        this.bookingJPARepository = bookingJPARepository;
    }

    @Override
    public List<Booking> getBookingsByVenueId(Long venueId) {
        List<BookingEntity> bookingEntities = bookingJPARepository.findByVenueId(venueId);
        return bookingEntities.stream()
                .map(BookingEntityTransformer::transformToBooking)
                .collect(Collectors.toList());
    }

    @Override
    public Booking getBookingByEventId(Long eventId) {
        BookingEntity bookingEntity = bookingJPARepository.findByEventId(eventId);
        return transformToBooking(bookingEntity);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        BookingEntity bookingEntity = transformToBookingEntity(booking);
        BookingEntity savedBookingEntity = bookingJPARepository.save(bookingEntity);
        return transformToBooking(savedBookingEntity);
    }

    @Override
    public void deleteById(Long bookingId) {
        bookingJPARepository.deleteById(bookingId);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        // Retrieve the existing booking entity
        BookingEntity existingBookingEntity = bookingJPARepository.findById(booking.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        // Check if the provided event ID matches the existing event ID
        // This is to ensure that the booking is associated with the correct event
        if (!existingBookingEntity.getEventId().equals(booking.getEventId())) {
            throw new IllegalArgumentException("Cannot update booking with different event ID");
        }

        // Transform the Booking object to a BookingEntity
        BookingEntity updatedBookingEntity = transformToBookingEntity(booking);

        // Save the updated booking entity
        updatedBookingEntity = bookingJPARepository.save(updatedBookingEntity);

        // Transform the updated booking entity back to a Booking object and return
        return transformToBooking(updatedBookingEntity);
    }
}

