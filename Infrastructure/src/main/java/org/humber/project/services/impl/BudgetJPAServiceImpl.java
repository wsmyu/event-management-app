package org.humber.project.services.impl;

import org.humber.project.domain.Budget;
import org.humber.project.entities.BudgetEntity;
import org.humber.project.repositories.BudgetJPARepository;
import org.humber.project.services.BudgetJPAService;
import org.humber.project.transformers.BudgetEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BudgetJPAServiceImpl implements BudgetJPAService {
    private final BudgetJPARepository repository;

    @Autowired
    public BudgetJPAServiceImpl(BudgetJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public Budget findBudgetByEventId(Long eventId) {
        return repository.findByEventId(eventId)
                .map(BudgetEntityTransformer::transformToBudget)
                .orElse(null); // Return null if no budget is found
    }

    @Override
    public Budget adjustBudget(Budget budget) {
        // Assume budget contains the adjustments
        // Find existing budget, apply adjustments, save, and return
        Budget existingBudget = findBudgetByEventId(budget.getEventId());
        // Apply the adjustments (this is simplified, adjust as needed)
        existingBudget.setVenueCost(budget.getVenueCost());
        existingBudget.setBeverageCostPerPerson(budget.getBeverageCostPerPerson());
        existingBudget.setGuestNumber(budget.getGuestNumber());
        // Recalculate totalBudget or any other necessary fields
        return saveBudget(existingBudget);
    }

    @Override
    public Budget saveBudget(Budget budget) {
        // Assuming BudgetEntityTransformer exists to convert between domain and entity
        BudgetEntity budgetEntity = BudgetEntityTransformer.transformToBudgetEntity(budget);
        BudgetEntity savedEntity = repository.save(budgetEntity);
        return BudgetEntityTransformer.transformToBudget(savedEntity);
    }
}
