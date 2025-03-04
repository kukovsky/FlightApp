package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ReservationProcessEntity;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationProcessJpaRepository extends JpaRepository<ReservationProcessEntity, Integer> {


    List<ReservationProcessEntity> findByReservation(ReservationsEntity reservation);
}