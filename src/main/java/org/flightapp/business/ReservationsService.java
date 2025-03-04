package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.ReservationStatus;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationsDAO reservationsDAO;
    private final UserService userService;


    public Reservations createReservation(String userName, Reservations reservations) {
        User existingUser = userService.findUserByUserName(userName);
        Reservations reservationToSave = reservations.withStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .withUser(existingUser)
                .withCreatedAt(LocalDateTime.now());
        Set<Reservations> existingReservations = existingUser.getReservations();
        existingReservations.add(reservationToSave);
        userService.saveReservation(existingUser.withReservations(existingReservations));
        return reservationToSave;
    }

    @Transactional
    public Reservations payReservation(Integer reservationId) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUserName(userName);
        Optional<Reservations> updatedUserReservation = user.getReservations().stream()
                .filter(reservation -> reservation.getReservationId().equals(reservationId))
                .map(reservation -> reservation.withStatus(ReservationStatus.PAID))
                .findFirst();
        System.out.println("Updated user reservation: " + updatedUserReservation);
        if(!updatedUserReservation.get().getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("User is not allowed to pay for this reservation");
        }
        return reservationsDAO.saveReservation(updatedUserReservation.get());
    }

    @Transactional
    public List<Reservations> findAllReservationsSorted(String userName) {
        return reservationsDAO.findByUserNameOrderStatusAscDepartureDateDesc(userName);
    }

    @Transactional
    public void deleteReservation(Integer reservationId) {
        reservationsDAO.deleteReservation(reservationId);
    }

    @Transactional
    public Reservations findReservationById(Integer reservationId) {
        Reservations reservations = reservationsDAO.findReservationById(reservationId);
        if (reservations == null) {
            throw new NotFoundException("Reservation not found");
        }
        System.out.println("Reservation found: " + reservations);
        return reservations;
    }
}
