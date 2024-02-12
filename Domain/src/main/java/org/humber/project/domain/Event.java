package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long eventId;
    private Long userId;
    private String eventName;
    private String eventType;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String eventDescription;

}
