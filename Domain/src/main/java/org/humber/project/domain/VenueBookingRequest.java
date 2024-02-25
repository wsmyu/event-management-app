package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueBookingRequest {
    private Long userId;
    private Long venueId;
    private Long eventId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
}