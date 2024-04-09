package org.humber.project.repositories;

import org.humber.project.entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    List<GuestEntity> findByEvent_EventId(Long eventId);
    List<GuestEntity> findByUser_UserId(Long userId);
    List<GuestEntity> findByUser_UserIdAndStatus(Long userId, String status);
}
