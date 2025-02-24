package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationsDAO reservationsDAO;


    @Transactional
    public Reservations save(Reservations reservations) {
        reservations = reservations.withCreatedAt(LocalDateTime.now());
        return reservationsDAO.saveReservation(reservations);

    }

    @Transactional
    public List<Reservations> findAllReservations(Integer userId) {
        return reservationsDAO.findByUserId(userId);
    }

    @Transactional
    public void deleteReservation(Integer reservationId) {
        reservationsDAO.deleteReservation(reservationId);
    }

    @Transactional
    public Reservations findReservationById(Integer reservationId) {
        Optional<Reservations> reservations = reservationsDAO.findReservationById(reservationId);
        if (reservations.isEmpty()) {
            throw new NotFoundException("Reservation not found");
        }
        return reservations.get();
    }

}
