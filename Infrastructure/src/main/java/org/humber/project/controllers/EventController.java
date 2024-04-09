package org.humber.project.controllers;


import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BudgetService;
import org.humber.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (EventValidationException e) {
            // If event validation fails, return a 400 Bad Request response with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.retrieveEventDetails(eventId);
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/eventType/{eventType}")
    public ResponseEntity<List<Event>> filterEventsByType(@PathVariable String eventType) {
        List<Event> events = eventService.filterEventsByType(eventType);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/date/{timeFrame}")
    public ResponseEntity<List<Event>> filterEventsByDate(@PathVariable String timeFrame) {
        List<Event> events = eventService.filterEventsByDate(timeFrame);
        return ResponseEntity.ok(events);
    }

    @GetMapping("city/{city}")
    public ResponseEntity<List<Event>> filterEventsByCity(@PathVariable String city) {
        List<Event> events = eventService.filterEventsByCity(city);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEventsWithFilters(@RequestParam(required = false) String eventName,
                                                               @RequestParam(required = false) String city,
                                                               @RequestParam(required = false) String eventType,
                                                               @RequestParam(required = false) String timeFrame) {
        List<Event> events = eventService.searchEventsWithFilters(eventName, city, eventType, timeFrame);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEventInfo(@PathVariable Long eventId, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEventInfo(eventId, event);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/delete")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().body("Event with ID " + eventId + " deleted successfully");
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete event");
        }
    }
}
