package org.humber.project.services;

import org.humber.project.domain.Budget;

public interface BudgetService {
    Budget createOrUpdateBudget(Budget budget);
    Budget findBudgetByEventId(Long eventId);
    Budget adjustBudget(Budget budget);
}
