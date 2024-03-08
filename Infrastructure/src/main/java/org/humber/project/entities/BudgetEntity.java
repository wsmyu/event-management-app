package org.humber.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class BudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "venue_cost")
    private Double venueCost;

    @Column(name = "beverage_cost_per_person")
    private Double beverageCostPerPerson;

    @Column(name = "guest_number")
    private Integer guestNumber;

    @Column(name = "total_budget")
    private Double totalBudget;
}
