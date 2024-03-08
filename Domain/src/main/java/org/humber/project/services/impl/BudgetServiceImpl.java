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
        Budget existingBudget = budgetJPAService.findBudgetByEventId(budget.getEventId());
        if (existingBudget == null) {
            // If there's no existing budget, create a new one.
            return budgetJPAService.saveBudget(budget);
        } else {
            // If there's an existing budget, update it with new details.
            existingBudget.setVenueCost(budget.getVenueCost());
            existingBudget.setBeverageCostPerPerson(budget.getBeverageCostPerPerson());
            existingBudget.setGuestNumber(budget.getGuestNumber());
            existingBudget.setTotalBudget(budget.getTotalBudget());
            return budgetJPAService.saveBudget(existingBudget);
        }
    }
    public Budget adjustBudget(Budget budget) {
        return budgetJPAService.adjustBudget(budget);
    }
    public Budget findBudgetByEventId(Long eventId) {
        return budgetJPAService.findBudgetByEventId(eventId);
    }


}