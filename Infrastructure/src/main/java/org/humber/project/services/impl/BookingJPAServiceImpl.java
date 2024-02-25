package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.entities.BookingEntity;
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
                .map(BookingEntityTransformer::transformToBooking) // Assuming you have a transformer class
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

}
