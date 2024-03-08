package org.humber.project.services.impl;

import org.humber.project.domain.Budget;
import org.humber.project.services.BudgetJPAService;

import org.humber.project.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetJPAService budgetJPAService;

    @Autowired
    public BudgetServiceImpl(BudgetJPAService budgetJPAService) {
        this.budgetJPAService = budgetJPAService;
    }

    @Override
    public Budget createOrUpdateBudget(Budget budget) {
        // Leverage BudgetJPAService for persistence operations
        return budgetJPAService.saveBudget(budget);
    }
    public Budget adjustBudget(Budget budget) {
        return budgetJPAService.adjustBudget(budget);
    }
    public Budget findBudgetByEventId(Long eventId) {
        return budgetJPAService.findBudgetByEventId(eventId);
    }


}
