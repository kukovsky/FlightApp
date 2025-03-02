package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.api.dto.UserDTO;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.api.dto.mapper.UsersMapper;
import org.flightapp.business.ReservationsService;
import org.flightapp.business.UserService;
import org.flightapp.domain.ReservationStatus;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Controller
@AllArgsConstructor
public class SearchFlightsController {

    private final UserService userService;
    private final UsersMapper usersMapper;
    private final ReservationsMapper reservationsMapper;
    private final ReservationsService reservationsService;

    @GetMapping("/flights")
    public String users(Model model) {
        return "flights";
    }

    @PostMapping("/reservations/reserve")
    public ResponseEntity<?> reserveFlight(@RequestBody ReservationsDTO reservationsDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        UserDTO userDTO = usersMapper.map(user);
        reservationsDTO.setUser(userDTO);
        reservationsDTO.setStatus(ReservationStatus.WAITING_FOR_PAYMENT);
        Reservations reservation = reservationsMapper.map(reservationsDTO);
        return ResponseEntity.ok(reservationsService.save(reservation));
    }
}
