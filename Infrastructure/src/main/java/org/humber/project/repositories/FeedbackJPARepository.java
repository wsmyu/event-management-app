package org.humber.project.repositories;

import org.humber.project.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackJPARepository extends JpaRepository<FeedbackEntity, Long> {
}
