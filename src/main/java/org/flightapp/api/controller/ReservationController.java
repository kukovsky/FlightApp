package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.flightapp.api.dto.mapper.ReservationsMapper;
import org.flightapp.business.ReservationsService;
import org.flightapp.domain.Reservations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@Controller
@AllArgsConstructor
public class ReservationController {

    private final ReservationsMapper reservationsMapper;
    private final ReservationsService reservationsService;


    @GetMapping("/reservations")
    public String reservations(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var allReservations = reservationsService.findAllReservationsSorted(userName).stream()
                .map(reservationsMapper::map).toList();
        model.addAttribute("allReservationsDTO", allReservations);
        return "reservations";
    }

    @PostMapping("/reservations/pay/{reservationNumber}")
    public String payReservation(@PathVariable String reservationNumber ,RedirectAttributes redirectAttributes) throws AccessDeniedException {
        reservationsService.payReservation(reservationNumber);
        redirectAttributes.addFlashAttribute("payMessage", "Rezerwacja opłacona pomyślnie");
        return "redirect:/reservations";
    }

    @PostMapping("/reservations/delete/{reservationNumber}")
    public String deleteReservation(@PathVariable String reservationNumber, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        Reservations reservation = reservationsService.findReservationByReservationNumber(reservationNumber);
        reservationsService.deleteReservation(reservation.getReservationNumber());
        redirectAttributes.addFlashAttribute("deleteMessage", "Rezerwacja usunięta pomyślnie");
        return "redirect:/reservations";
    }



}
