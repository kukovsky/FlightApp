package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.Reservations;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReservationsRepository implements ReservationsDAO {

    private final ReservationsJpaRepository reservationsJpaRepository;
    private final SourceTargetMapper sourceTargetMapper;
    private JpaContext jpaContext;

    @Override
    public Reservations findReservationByReservationNumber(String reservationNumber) {
        ReservationsEntity reservationsEntity = reservationsJpaRepository.findByReservationNumber(reservationNumber);
        return sourceTargetMapper.fromEntity(reservationsEntity, jpaContext);
    }

    @Override
    public List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName) {
        return reservationsJpaRepository.findByUserUserNameOrderByStatusAscDepartureDateAsc(userName).stream()
                .map(reservationsEntity -> sourceTargetMapper.fromEntity(reservationsEntity, jpaContext))
                .toList();
    }


    @Override
    public void deleteReservation(String reservationNumber) {
        reservationsJpaRepository.deleteByReservationNumber(reservationNumber);
    }

    @Override
    public void saveReservation(Reservations updatedReservation) {
        System.out.println("Updated reservation: " + updatedReservation);
        ReservationsEntity reservationsEntity = sourceTargetMapper.toEntity(updatedReservation, jpaContext);
        System.out.println("Updated reservation entity: " + reservationsEntity);
        reservationsJpaRepository.save(reservationsEntity);
    }


}
