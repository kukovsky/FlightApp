package org.flightapp.business.dao;

import org.flightapp.domain.Reservations;

import java.util.List;
import java.util.Optional;

public interface ReservationsDAO {

    Optional<Reservations> findReservationByReservationNumber(String reservationNumber);
    List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName);
    void deleteReservation(String reservationNumber);

    void saveReservation(Reservations updatedReservation);
}
