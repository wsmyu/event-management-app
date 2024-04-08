package org.humber.project.services.impl;

import org.humber.project.domain.Guest;
import org.humber.project.entities.GuestEntity;
import org.humber.project.exceptions.EntityNotFoundException;
import org.humber.project.repositories.GuestRepository;
import org.humber.project.services.GuestJPAService;
import org.humber.project.transformers.GuestEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestJPAServiceImpl implements GuestJPAService {

    private final GuestRepository guestRepository;
    private final GuestEntityTransformer guestEntityTransformer;

    @Autowired
    public GuestJPAServiceImpl(GuestRepository guestRepository, GuestEntityTransformer guestEntityTransformer) {
        this.guestRepository = guestRepository;
        this.guestEntityTransformer = guestEntityTransformer;
    }

    @Override
    public Guest manageGuest(Guest guest) {
        GuestEntity guestEntity = guestEntityTransformer.transformToGuestEntity(guest);
        GuestEntity savedEntity = guestRepository.save(guestEntity);
        return guestEntityTransformer.transformToGuest(savedEntity);
    }

    @Override
    public List<Guest> getGuestListByEventId(Long eventId) {
        return guestRepository.findByEvent_EventId(eventId).stream()
                .map(guestEntityTransformer::transformToGuest)
                .collect(Collectors.toList());
    }
    @Override
    public Guest updateGuestStatus(Long guestId, String status) {
        GuestEntity guestEntity = guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        guestEntity.setStatus(status);
        GuestEntity updatedEntity = guestRepository.save(guestEntity);
        return guestEntityTransformer.transformToGuest(updatedEntity);
    }
    @Override
    public List<Guest> getInvitationsByUserId(Long userId) {
        // Assuming that you have a method in your GuestRepository to find by userId
        List<GuestEntity> guestEntities = guestRepository.findByUser_UserId(userId);
        return guestEntities.stream()
                .map(guestEntityTransformer::transformToGuest)
                .collect(Collectors.toList());
    }

}
