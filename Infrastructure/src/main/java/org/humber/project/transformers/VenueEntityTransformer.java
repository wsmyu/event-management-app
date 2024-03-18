package org.humber.project.transformers;

import org.humber.project.domain.Venue;
import org.humber.project.entities.VenueEntity;

import java.util.List;
import java.util.stream.Collectors;

public class VenueEntityTransformer {

    public static VenueEntity transformToVenueEntity(Venue venue){
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setVenueId(venue.getVenueId());
        venueEntity.setVenueName(venueEntity.getVenueName());
        venueEntity.setCity(venueEntity.getCity());
        venueEntity.setCountry(venueEntity.getCountry());
        venueEntity.setAddress(venueEntity.getAddress());
        venueEntity.setCapacity(venue.getCapacity());
        venueEntity.setDescription(venue.getDescription());
        venueEntity.setImageUrl(venue.getImageUrl());
        venueEntity.setRating(venue.getRating());
        return venueEntity;
    }

    public static Venue transformToVenue(VenueEntity venueEntity){
        return Venue.builder()
                .venueId(venueEntity.getVenueId())
                .venueName(venueEntity.getVenueName())
                .capacity(venueEntity.getCapacity())
                .city(venueEntity.getCity())
                .country(venueEntity.getCountry())
                .address(venueEntity.getAddress())
                .description(venueEntity.getDescription())
                .imageUrl(venueEntity.getImageUrl())
                .rating(venueEntity.getRating())
                .build();
    }

    public static List<Venue> transformToVenues(List<VenueEntity> entities) {
        return entities.stream()
                .map(VenueEntityTransformer::transformToVenue)
                .collect(Collectors.toList());
    }
}
