package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UsersEntity, Integer> {

    UsersEntity findByEmail(String userId);

    UsersEntity findByUserName(String userName);

    @Query("SELECT u FROM UsersEntity u LEFT JOIN FETCH u.reservations WHERE u.userName = :userName")
    UsersEntity findByUserNameWithReservations(@Param("userName") String userName);





}
