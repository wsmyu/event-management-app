package org.humber.project.controllers;

import org.humber.project.domain.Budget;
import org.humber.project.domain.Event;
import org.humber.project.services.BudgetService;
import org.humber.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private final EventService eventService;
    private final BudgetService budgetService;

    @Autowired
    public BudgetController(EventService eventService, BudgetService budgetService) {
        this.eventService = eventService;
        this.budgetService = budgetService;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<?> createOrUpdateBudget(@PathVariable Long eventId, @RequestBody Budget budget) {
        try {
            Event event = eventService.retrieveEventDetails(eventId);
            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + eventId);
            }
            budget.setEventId(eventId);
            Budget updatedBudget = budgetService.createOrUpdateBudget(budget);
            return ResponseEntity.ok(updatedBudget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create or update budget: " + e.getMessage());
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getBudgetByEventId(@PathVariable Long eventId) {
        try {
            Budget budget = budgetService.findBudgetByEventId(eventId);
            if (budget == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Budget not found for event ID: " + eventId);
            }
            return ResponseEntity.ok(budget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving budget: " + e.getMessage());
        }
    }

    @GetMapping("/{eventId}/detail")
    public ResponseEntity<Budget> getEventBudgetDetail(@PathVariable Long eventId) {
        try {
            Budget budget = budgetService.findBudgetByEventId(eventId);
            if (budget == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(budget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{eventId}/adjust")
    public ResponseEntity<Budget> adjustEventBudget(@PathVariable Long eventId, @RequestBody Budget budgetDetails) {
        try {
            budgetDetails.setEventId(eventId);
            Budget adjustedBudget = budgetService.adjustBudget(budgetDetails);
            return ResponseEntity.ok(adjustedBudget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
