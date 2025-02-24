package org.flightapp.business.dao;

import org.flightapp.domain.Reservations;

import java.util.List;
import java.util.Optional;

public interface ReservationsDAO {

    Optional<Reservations> findReservationById(Integer reservationId);
    List<Reservations> findByUserId(Integer userId);
    Reservations saveReservation(Reservations reservations);
    void deleteReservation(Integer reservationId);

}
