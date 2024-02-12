package org.humber.project.repositories;

import org.humber.project.domain.User;
import org.humber.project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository <UserEntity, Long>{

}
