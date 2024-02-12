package org.humber.project.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Entity
@Table(name = "venues")
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long venueId;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description")
    private String description;
}

//    @Column(name = "available_date")
//    private LocalDate availableDate;
//
//    @Column(name = "available_time")
//    private LocalTime availableTime;