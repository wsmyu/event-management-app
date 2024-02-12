package org.humber.project.repositories;

import org.humber.project.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJPARepository extends JpaRepository<EventEntity,Long> {

}
