package org.humber.project.transformers;

import org.humber.project.domain.Budget;
import org.humber.project.entities.BudgetEntity;

public class BudgetEntityTransformer {
    public static Budget transformToBudget(BudgetEntity budgetEntity) {
        return Budget.builder()
                .id(budgetEntity.getId())
                .eventId(budgetEntity.getEventId())
                .venueCost(budgetEntity.getVenueCost())
                .beverageCostPerPerson(budgetEntity.getBeverageCostPerPerson())
                .guestNumber(budgetEntity.getGuestNumber())
                .totalBudget(budgetEntity.getTotalBudget())
                .build();
    }

    public static BudgetEntity transformToBudgetEntity(Budget budget) {
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(budget.getId());
        budgetEntity.setEventId(budget.getEventId());
        budgetEntity.setVenueCost(budget.getVenueCost());
        budgetEntity.setBeverageCostPerPerson(budget.getBeverageCostPerPerson());
        budgetEntity.setGuestNumber(budget.getGuestNumber());
        budgetEntity.setTotalBudget(budget.getTotalBudget());
        return budgetEntity;
    }
    private static Double calculateTotalBudget(BudgetEntity budgetEntity) {
        // Example calculation, adjust according to your needs
        return budgetEntity.getVenueCost() + budgetEntity.getBeverageCostPerPerson() * budgetEntity.getGuestNumber();
    }
}
