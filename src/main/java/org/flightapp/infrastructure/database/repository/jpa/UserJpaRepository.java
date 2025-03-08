package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByEmail(String userId);

    Optional<UsersEntity> findByUserName(String userName);

    @Query("SELECT u FROM UsersEntity u LEFT JOIN FETCH u.reservations WHERE u.userName = :userName")
    Optional<UsersEntity> findByUserNameWithReservations(@Param("userName") String userName);





}
