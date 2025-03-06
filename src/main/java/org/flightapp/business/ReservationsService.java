package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.ReservationStatus;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationsDAO reservationsDAO;
    private final UserService userService;


    public Reservations createReservation(String userName, Reservations reservations) {
        User existingUser = userService.findUserByUserNameWithReservations(userName);
        Reservations reservationToSave = reservations
                .withReservationNumber(UUID.randomUUID().toString().substring(0, 8))
                .withStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .withUser(existingUser)
                .withCreatedAt(LocalDateTime.now());
        Set<Reservations> existingReservations = existingUser.getReservations();
        existingReservations.add(reservationToSave);
        userService.saveReservation(existingUser.withReservations(existingReservations));
        return reservationToSave;
    }

    @Transactional
    public void payReservation(String reservationNumber) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userService.findUserByUserNameWithReservations(userName);
        Reservations reservation = reservationsDAO.findReservationByReservationNumber(reservationNumber);
        if (!reservation.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("You are not allowed to pay for this reservation");
        }
        Reservations updatedReservation = reservation.withStatus(ReservationStatus.PAID)
                .withUser(existingUser);
        reservationsDAO.saveReservation(updatedReservation);
    }

    @Transactional
    public void deleteReservation(String reservationNumber) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Reservations reservation = reservationsDAO.findReservationByReservationNumber(reservationNumber);
        if (!reservation.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("You are not allowed to delete this reservation");
        }
        System.out.println("Deleting reservation: " + reservationNumber);
        reservationsDAO.deleteReservation(reservationNumber);
    }

    @Transactional
    public List<Reservations> findAllReservationsSorted(String userName) {
        return reservationsDAO.findByUserNameOrderStatusAscDepartureDateDesc(userName);
    }

    @Transactional
    public Reservations findReservationByReservationNumber(String reservationNumber) {
        Reservations reservations = reservationsDAO.findReservationByReservationNumber(reservationNumber);
        if (reservations == null) {
            throw new NotFoundException("Reservation not found");
        }
        System.out.println("Reservation found: " + reservations);
        return reservations;
    }
}
