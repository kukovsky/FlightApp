package org.flightapp.api.controller.rest;

import lombok.AllArgsConstructor;
import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.business.ReservationsService;
import org.flightapp.business.UserService;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationsRestController {

    private final UserService userService;
    private final ReservationsMapper reservationsMapper;
    private final ReservationsService reservationsService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveFlight(
            @RequestBody ReservationsDTO reservationsDTO
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Reservations reservation = reservationsMapper.map(reservationsDTO);
        Reservations savedReservation = reservationsService.createReservation(userName, reservation);
        System.out.println("Reservation saved: " + savedReservation);
        return ResponseEntity.ok(Map.of(
                "message", "Rezerwacja zapisana!",
                "status", savedReservation.getStatus()
        ));
    }
}
