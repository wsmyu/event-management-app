package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long taskId;
    private Long eventId;
    private Long userId;
    private String taskDescription;
    private boolean status; // true for completed, false for incomplete
    private LocalDate eventDate;
}