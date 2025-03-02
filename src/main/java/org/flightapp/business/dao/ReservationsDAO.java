package org.flightapp.business.dao;

import org.flightapp.domain.Reservations;

import java.util.List;

public interface ReservationsDAO {

    Reservations findReservationById(Integer reservationId);
    List<Reservations> findByUserNameOrderStatusAscDepartureDateDesc(String userName);
    Reservations saveReservation(Reservations reservations);
    void deleteReservation(Integer reservationId);
    void deleteAll();

}
