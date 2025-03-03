package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.api.dto.mapper.UsersMapper;
import org.flightapp.business.ReservationsService;
import org.flightapp.business.UserService;
import org.flightapp.domain.ReservationStatus;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@Controller
@AllArgsConstructor
public class ReservationController {

    private final UserService userService;
    private final ReservationsMapper reservationsMapper;
    private final ReservationsService reservationsService;
    private final UsersMapper usersMapper;


    @GetMapping("/reservations")
    public String reservations(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        var allReservations = reservationsService.findAllReservationsSorted(userName).stream()
                .map(reservationsMapper::map).toList();
        model.addAttribute("allReservationsDTO", allReservations);
        return "reservations";
    }

    @PostMapping("/reservations/pay/{reservationId}")
    public String payReservation(@PathVariable Integer reservationId ,RedirectAttributes redirectAttributes) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Reservations paidReservation = reservationsService.payReservation(reservationId, userName);
        reservationsMapper.map(paidReservation);
        redirectAttributes.addFlashAttribute("payMessage", "Rezerwacja opłacona pomyślnie");
        return "redirect:/reservations";
    }

    @PostMapping("/reservations/delete/{reservationId}")
    public String deleteReservation(@PathVariable Integer reservationId, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        Reservations reservation = reservationsService.findReservationById(reservationId);
        reservationsService.deleteReservation(reservation.getReservationId());
        redirectAttributes.addFlashAttribute("deleteMessage", "Rezerwacja usunięta pomyślnie");
        return "redirect:/reservations";
    }



}
