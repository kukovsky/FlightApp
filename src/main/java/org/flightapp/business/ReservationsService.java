package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.api.dto.mapper.UsersMapper;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.ReservationStatus;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
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
    private final ReservationsMapper reservationsMapper;
    private final UsersMapper usersMapper;


    @Transactional
    public Reservations createReservation(String userName, Reservations reservations) {
        User existingUser = userService.findUserByUserName(userName);
        Reservations reservationToSave = reservations.withStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .withUser(existingUser)
                .withCreatedAt(LocalDateTime.now());
        Set<Reservations> existingReservations = existingUser.getReservations();
        existingReservations.add(reservationToSave);
        userService.saveReservation(existingUser.withReservations(existingReservations));
        System.out.println("Reservation saved: " + reservationToSave);
        return reservationToSave;
    }

    @Transactional
    public Reservations payReservation(Integer reservationId, String userName) throws AccessDeniedException {
        Reservations reservation = findReservationById(reservationId);

//        return reservationsDAO.saveReservation(reservationToSave);
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
        return reservations;
    }
}
