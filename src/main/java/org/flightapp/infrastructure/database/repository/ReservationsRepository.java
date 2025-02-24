package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.Reservations;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.ReservationsEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReservationsRepository implements ReservationsDAO {

    private final ReservationsJpaRepository reservationsJpaRepository;
    private final ReservationsEntityMapper reservationsEntityMapper;


    @Override
    public Optional<Reservations> findReservationById(Integer reservationId) {
        return reservationsJpaRepository.findByReservationId(reservationId)
                .map(reservationsEntityMapper::mapFromEntity);
    }

    @Override
    public List<Reservations> findByUserId(Integer userId) {
        return reservationsJpaRepository.findByUserUserId(userId).stream()
                .map(reservationsEntityMapper::mapFromEntity)
                .toList();
    }


    @Override
    public Reservations saveReservation(Reservations reservations) {
        ReservationsEntity reservationsEntity = reservationsEntityMapper.mapToEntity(reservations);
        ReservationsEntity savedEntity = reservationsJpaRepository.save(reservationsEntity);
        return reservationsEntityMapper.mapFromEntity(savedEntity);
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        reservationsJpaRepository.deleteById(reservationId);
    }


}
