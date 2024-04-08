package org.humber.project.repositories;

import org.humber.project.entities.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BudgetJPARepository extends JpaRepository<BudgetEntity, Long> {
    Optional<BudgetEntity> findByEventId(Long eventId);

}
