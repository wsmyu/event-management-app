package org.humber.project.services;

import org.humber.project.domain.Budget;

public interface BudgetJPAService {
    Budget saveBudget(Budget budget);
    Budget findBudgetByEventId(Long eventId);
    Budget adjustBudget(Budget budget);
}
