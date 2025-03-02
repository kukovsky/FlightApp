package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsJpaRepository extends JpaRepository<ReservationsEntity, Integer> {

    List<ReservationsEntity> findByUserUserNameOrderByStatusAscDepartureDateAsc(String userName);
    ReservationsEntity findByReservationId(Integer reservationId);
    void deleteByReservationId(Integer id);


}
