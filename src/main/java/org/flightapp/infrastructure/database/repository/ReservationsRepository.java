package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.Reservations;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReservationsRepository implements ReservationsDAO {

    private final ReservationsJpaRepository reservationsJpaRepository;
    private final SourceTargetMapper sourceTargetMapper;

    @Override
    public Reservations findReservationById(Integer reservationId) {
        ReservationsEntity reservationsEntity = reservationsJpaRepository.findByReservationIdWithUser(reservationId);
        return sourceTargetMapper.fromEntity(reservationsEntity);

    }

    @Override
    public List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName) {
        return reservationsJpaRepository.findByUserUserNameOrderByStatusAscDepartureDateAsc(userName).stream()
                .map((ReservationsEntity entity) -> sourceTargetMapper.fromEntity(entity))
                .toList();
    }


    @Override
    public Reservations saveReservation(Reservations reservations) {
        ReservationsEntity toSave = sourceTargetMapper.toEntity(reservations);
        ReservationsEntity savedEntity = reservationsJpaRepository.save(toSave);
        return sourceTargetMapper.fromEntity(savedEntity);
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        reservationsJpaRepository.deleteByReservationId(reservationId);
    }



}
