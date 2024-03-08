package org.humber.project.repositories;

import org.humber.project.domain.User;
import org.humber.project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository <UserEntity, Long>{

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByUsernameContaining(String username);
}
