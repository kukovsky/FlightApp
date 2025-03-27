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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationsDAO reservationsDAO;
    private final UserService userService;

    @Transactional
    public Reservations createReservation(String userName, Reservations reservations) {
        User existingUser = userService.findUserByUserNameWithReservations(userName);
        if (existingUser == null){
            throw new NotFoundException("Użytkownik nieznaelziony");
        }
        Reservations reservationToSave = reservations
                .withReservationNumber(UUID.randomUUID().toString().substring(0, 8))
                .withStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .withUser(existingUser)
                .withCreatedAt(LocalDateTime.now());
        Set<Reservations> existingReservations = existingUser.getReservations();
        existingReservations.add(reservationToSave);
        userService.saveReservation(existingUser.withReservations(existingReservations));
        log.info("Rezerwacja zapisana: {}", reservationToSave.getReservationNumber());
        return reservationToSave;
    }

    @Transactional
    public void payReservation(String reservationNumber) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userService.findUserByUserNameWithReservations(userName);
        Reservations reservation = findReservationByReservationNumber(reservationNumber);
        if (!reservation.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie jesteś uprawniony do opłacenia tej rezerwacji");
        }
        Reservations updatedReservation = reservation.withStatus(ReservationStatus.PAID)
                .withUser(existingUser);
        reservationsDAO.saveReservation(updatedReservation);
        log.info("Rezerwacja zapłacona: {}", reservationNumber);
    }

    @Transactional
    public void deleteReservation(String reservationNumber) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Reservations reservation = findReservationByReservationNumber(reservationNumber);
        if (!reservation.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie jesteś uprawniony do usunięcia tej rezerwacji");
        }
        reservationsDAO.deleteReservation(reservationNumber);
        log.info("Rezerwacja usunięta: {}", reservationNumber);
    }

    @Transactional
    public List<Reservations> findAllReservationsSorted(String userName) {
        return reservationsDAO.findByUserNameOrderStatusAscDepartureDateDesc(userName);
    }

    @Transactional
    public Reservations findReservationByReservationNumber(String reservationNumber) {
        Optional<Reservations> reservations = reservationsDAO.findReservationByReservationNumber(reservationNumber);
        if (reservations.isEmpty()) {
            throw new NotFoundException("Rezerwacja nie znaleziona");
        }
        log.info("Znaleziono rezerwację: {}", reservationNumber);
        return reservations.get();
    }
}
