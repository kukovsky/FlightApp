package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<FlightAppUsersEntity, Integer> {

    Optional<FlightAppUsersEntity> findByEmail(String userId);
    Optional<FlightAppUsersEntity> findByUserName(String userName);



}
