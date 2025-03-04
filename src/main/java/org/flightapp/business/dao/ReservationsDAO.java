package org.flightapp.business.dao;

import org.flightapp.domain.Reservations;

import java.util.List;

public interface ReservationsDAO {

    Reservations findReservationByReservationNumber(String reservationNumber);
    List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName);
    void deleteReservation(String reservationNumber);

    void saveReservation(Reservations updatedReservation);
}
