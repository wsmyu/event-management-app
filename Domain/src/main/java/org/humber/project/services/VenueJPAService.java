package org.humber.project.services;

import org.humber.project.domain.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueJPAService {
    Venue createVenue(Venue venue);
    Venue getVenueById(Long id);
    List<Venue> getAllVenues();
//    Venue updateVenue(Long id, Venue updatedVenue);

}
