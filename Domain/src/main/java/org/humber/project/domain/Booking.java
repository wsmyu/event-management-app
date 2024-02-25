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
public class Booking {
    private Long bookingId;
    private Long eventId;
    private Long userId;
    private Long venueId;
    private LocalTime bookingStartTime;
    private LocalTime bookingEndTime;
    private LocalDate bookingCreationDate;
}
