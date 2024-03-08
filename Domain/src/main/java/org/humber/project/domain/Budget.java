package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    private Long id;
    private Long eventId;
    private Double venueCost;
    private Double beverageCostPerPerson;
    private Integer guestNumber;
    private Double totalBudget;
}
