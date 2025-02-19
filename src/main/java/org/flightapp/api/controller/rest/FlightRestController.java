package org.flightapp.api.controller.rest;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import lombok.AllArgsConstructor;
import org.flightapp.business.AmadeusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(FlightRestController.API_FLIGHT)
public class FlightRestController {

    public static final String API_FLIGHT = "/api/flights";

    private final AmadeusService amadeusService;

    @GetMapping
    public FlightOfferSearch[] getFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam String adults
    ) throws ResponseException {
        return amadeusService.getFlights(origin, destination, departDate, returnDate, adults);
    }
}
