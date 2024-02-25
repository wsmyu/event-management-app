package org.humber.project.repositories;

import org.humber.project.domain.Booking;
import org.humber.project.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingJPARepository extends JpaRepository<BookingEntity,Long> {
    List<BookingEntity> findByVenueId(Long venueId);
}
