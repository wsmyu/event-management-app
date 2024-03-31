package org.humber.project.repositories;

import org.humber.project.entities.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendJPARepository extends JpaRepository<FriendEntity,Long>{
}
