package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersJpaRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String email);
}
