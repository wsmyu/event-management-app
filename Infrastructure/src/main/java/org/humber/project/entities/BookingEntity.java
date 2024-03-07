package org.humber.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "venue_id")
    private Long venueId;

    @Column(name="booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_start_time")
    private LocalTime bookingStartTime;

    @Column(name = "booking_end_time")
    private LocalTime bookingEndTime;

    @Column(name = "booking_creation_date")
    private LocalDate bookingCreationDate;

}
