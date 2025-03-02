package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.ReservationsDAO;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationsDAO reservationsDAO;
    private final UserService userService;


    @Transactional
    public Reservations save(Reservations reservations) {
        reservations = buildReseravtion(reservations, reservations.getUser());
        User existingUser = userService.findUserByUserName(reservations.getUser().getUserName());
        Set<Reservations> existingReservations = existingUser.getReservations();
        existingReservations.add(reservations);
        userService.saveReservation(existingUser.withReservations(existingReservations));
        return reservations;
    }

    private Reservations buildReseravtion(Reservations reservations, User user) {
        return Reservations.builder()
                .departureOrigin(reservations.getDepartureOrigin())
                .departureDestination(reservations.getDepartureDestination())
                .departureDate(reservations.getDepartureDate())
                .departureReturnDate(reservations.getDepartureReturnDate())
                .departureAirline(reservations.getDepartureAirline())
                .departureFlightNumber(reservations.getDepartureFlightNumber())
                .returnOrigin(reservations.getReturnOrigin())
                .returnDestination(reservations.getReturnDestination())
                .returnDepartureDate(reservations.getReturnDepartureDate())
                .returnReturnDate(reservations.getReturnReturnDate())
                .returnAirline(reservations.getReturnAirline())
                .returnFlightNumber(reservations.getReturnFlightNumber())
                .price(reservations.getPrice())
                .currency(reservations.getCurrency())
                .numberOfPassengers(reservations.getNumberOfPassengers())
                .createdAt(LocalDateTime.now())
                .status(reservations.getStatus())
                .user(user)
                .build();
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
