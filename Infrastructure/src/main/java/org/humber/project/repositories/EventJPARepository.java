package org.humber.project.repositories;
import org.humber.project.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventJPARepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByEventNameContaining(String eventName);

    List<EventEntity> findByEventType(String eventType);

    @Query("SELECT e FROM EventEntity e JOIN VenueEntity v ON e.venueId = v.venueId WHERE v.city = :city")
    List<EventEntity> findByCity(@Param("city") String city);

    List<EventEntity> findByEventDateBetween(LocalDate startDate, LocalDate endDate);

}
