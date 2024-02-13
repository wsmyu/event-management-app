package org.humber.project.services.impl;

import org.humber.project.domain.Venue;
import org.humber.project.entities.VenueEntity;
import org.humber.project.repositories.VenueJPARepository;
import org.humber.project.services.VenueJPAService;
import org.humber.project.transformers.EventEntityTransformer;
import org.humber.project.transformers.VenueEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueJPAServiceImpl implements VenueJPAService {
    private final VenueJPARepository venueJPARepository;

    @Autowired
    public VenueJPAServiceImpl(VenueJPARepository venueJPARepository) {
        this.venueJPARepository = venueJPARepository;
    }
    @Override
    public Venue createVenue(Venue venue) {
        VenueEntity venueEntity = VenueEntityTransformer.transformToVenueEntity(venue);
        VenueEntity savedVenueEntity = venueJPARepository.save(venueEntity);
        return VenueEntityTransformer.transformToVenue(savedVenueEntity);
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
