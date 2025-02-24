package org.flightapp.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.api.dto.UserDTO;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.business.ReservationsService;
import org.flightapp.business.UserService;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class ReservationController {

    private final UserService userService;
    private final ReservationsMapper reservationsMapper;
    private ReservationsService reservationsService;

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (Objects.isNull(username)) {
            return "redirect:/login";
        }
        Integer userId = userService.findUserByUserName(username).getUserId();
        List<Reservations> reservations = reservationsService.findAllReservations(userId);
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @PostMapping("/reservations/reserve")
    public ResponseEntity<?> reserveFlight(@RequestBody ReservationsDTO reservationsDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authenticated");
        }
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (reservationsDTO.getUser() == null) {
            reservationsDTO.setUser(new UserDTO());
        }
        reservationsDTO.getUser().setUserId(user.getUserId());

        Reservations reservations = reservationsMapper.map(reservationsDTO);
        reservations = reservations.withUser(user);
        return ResponseEntity.ok(reservationsService.save(reservations));
    }

    @PostMapping("/reservations/delete/{reservationId}")
    public String deleteReservation(@PathVariable Integer reservationId) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        String userName = auth.getName();
        Reservations reservation = reservationsService.findReservationById(reservationId);
        if (reservation == null || reservation.getUser() == null || !reservation.getUser().getUserName().equals(userName)) {
            reservationsService.deleteReservation(reservationId);
        } else {
            throw new AccessDeniedException("User not allowed to delete reservation");
        }
        return "redirect:/reservations";
    }

}
