package org.humber.project.transformers;

import org.humber.project.domain.Guest;
import org.humber.project.entities.GuestEntity;
import org.humber.project.repositories.EventJPARepository; // Assuming this exists
import org.humber.project.repositories.UserJPARepository; // Assuming this exists
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuestEntityTransformer {

    private final UserJPARepository userRepository;
    private final EventJPARepository eventRepository;

    @Autowired
    public GuestEntityTransformer(UserJPARepository userRepository, EventJPARepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public GuestEntity transformToGuestEntity(Guest guest) {
        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setGuestId(guest.getGuestId());
        guestEntity.setUser(userRepository.findById(guest.getUserId()).orElse(null));
        guestEntity.setEvent(eventRepository.findById(guest.getEventId()).orElse(null));
        guestEntity.setStatus(guest.getStatus());
        return guestEntity;
    }

    public Guest transformToGuest(GuestEntity guestEntity) {
        return Guest.builder()
                .guestId(guestEntity.getGuestId())
                .userId(guestEntity.getUser().getUserId()) // Make sure these method names match your UserEntity's methods
                .eventId(guestEntity.getEvent().getEventId()) // Make sure these method names match your EventEntity's methods
                .status(guestEntity.getStatus())
                .build();
    }
}
