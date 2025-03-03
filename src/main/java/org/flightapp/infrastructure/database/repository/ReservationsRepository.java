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

    JpaContext jpaCtx = new JpaContext(null);

    @Override
    public Reservations findReservationById(Integer reservationId) {
        ReservationsEntity reservationsEntity = reservationsJpaRepository.findByReservationId(reservationId);
        return sourceTargetMapper.fromEntity(reservationsEntity, jpaCtx);

    }

    @Override
    public List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName) {
        return reservationsJpaRepository.findByUserUserNameOrderByStatusAscDepartureDateAsc(userName).stream()
                .map((ReservationsEntity entity) -> sourceTargetMapper.fromEntity(entity, jpaCtx))
                .toList();
    }


    @Override
    public Reservations saveReservation(Reservations reservations) {
        ReservationsEntity toSave = sourceTargetMapper.toEntity(reservations, jpaCtx);
        ReservationsEntity savedEntity = reservationsJpaRepository.save(toSave);
        return sourceTargetMapper.fromEntity(savedEntity, jpaCtx);
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        reservationsJpaRepository.deleteByReservationId(reservationId);
    }

    @Override
    public void deleteAll() {
        reservationsJpaRepository.deleteAll();
    }


}
