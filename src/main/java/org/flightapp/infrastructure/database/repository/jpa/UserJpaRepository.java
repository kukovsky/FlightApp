package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<FlightAppUsersEntity, Integer> {

    FlightAppUsersEntity findByEmail(String userId);
    FlightAppUsersEntity findByUserName(String userName);

    @Query("SELECT u FROM FlightAppUsersEntity u")
    FlightAppUsersEntity findAllUsers();




}
