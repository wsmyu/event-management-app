package org.humber.project.services.impl;

import org.humber.project.domain.Venue;
import org.humber.project.repositories.VenueJPARepository;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventService;
import org.humber.project.services.VenueJPAService;
import org.humber.project.transformers.VenueEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Lazy
public class VenueJPAServiceImpl implements VenueJPAService {
    private final VenueJPARepository venueJPARepository;

    @Autowired
    public VenueJPAServiceImpl(VenueJPARepository venueJPARepository, @Lazy BookingService bookingService, @Lazy EventService eventService) {
        this.venueJPARepository = venueJPARepository;
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

}
