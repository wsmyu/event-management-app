package org.humber.project.repositories;

import org.humber.project.entities.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueJPARepository extends JpaRepository<VenueEntity,Long> {
}
