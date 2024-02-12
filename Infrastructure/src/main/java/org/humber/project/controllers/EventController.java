package org.humber.project.controllers;

import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.services.EventJPAService;
import org.humber.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.retrieveEventDetails(eventId);
        return ResponseEntity.ok().body(event);
    }

    @PutMapping("/{eventId}/update")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(eventId, event);
        return ResponseEntity.ok().body(updatedEvent);
    }
}
