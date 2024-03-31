package org.humber.project.controllers;


import org.humber.project.domain.Budget;
import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BudgetService;
import org.humber.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final BudgetService budgetService;
    @Autowired
    public EventController(EventService eventService, BudgetService budgetService) {
        this.eventService = eventService;
        this.budgetService = budgetService;
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (EventValidationException e) {
            // If event validation fails, return a 400 Bad Request response with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create event: " + e.getMessage());
        }
    }
    @PostMapping("/{eventId}/budget")
    public ResponseEntity<?> createOrUpdateBudget(@PathVariable Long eventId, @RequestBody Budget budget) {
        try {
            // Check if the event exists
            Event event = eventService.retrieveEventDetails(eventId);
            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + eventId);
            }

            // Set the eventId for the budget to ensure it is linked to the correct event
            budget.setEventId(eventId);

            // Create or update the budget
            Budget updatedBudget = budgetService.createOrUpdateBudget(budget);
            return ResponseEntity.ok(updatedBudget);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create or update budget: " + e.getMessage());
        }
    }

    @GetMapping("/{eventId}/budget")
    public ResponseEntity<?> getBudgetByEventId(@PathVariable Long eventId) {
        try {
            Event event = eventService.retrieveEventDetails(eventId);
            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + eventId);
            }

            Budget budget = budgetService.findBudgetByEventId(eventId);
            if (budget == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Budget not found for event ID: " + eventId);
            }

            return ResponseEntity.ok(budget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving budget: " + e.getMessage());
        }
    }

    @GetMapping("/{eventId}/budget/detail")
    public ResponseEntity<Budget> getEventBudgetDetail(@PathVariable Long eventId) {
        Budget budget = budgetService.findBudgetByEventId(eventId);
        return ResponseEntity.ok(budget);
    }
    @PostMapping("/{eventId}/budget/adjust")
    public ResponseEntity<Budget> adjustEventBudget(@PathVariable Long eventId, @RequestBody Budget budgetDetails) {
        // Ensure the budgetDetails has the correct eventId set
        budgetDetails.setEventId(eventId);
        Budget adjustedBudget = budgetService.adjustBudget(budgetDetails);
        return ResponseEntity.ok(adjustedBudget);
    }


    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.retrieveEventDetails(eventId);
        return ResponseEntity.ok().body(event);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEventInfo(@PathVariable Long eventId, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEventInfo(eventId, event);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create event: " + e.getMessage());
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
