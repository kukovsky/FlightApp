package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsJpaRepository extends JpaRepository<ReservationsEntity, Integer> {

    List<ReservationsEntity> findByUserUserNameOrderByStatusAscDepartureDateAsc(String userName);

    @Query("SELECT r FROM ReservationsEntity r JOIN FETCH r.user WHERE r.reservationId = :id")
    ReservationsEntity findByReservationIdWithUser(@Param("id") Integer reservationId);

    void deleteByReservationNumber(String reservationNumber);


    ReservationsEntity findByReservationNumber(String reservationNumber);
}
