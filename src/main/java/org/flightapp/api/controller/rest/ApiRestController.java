package org.flightapp.api.controller.rest;


import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Location;
import lombok.AllArgsConstructor;
import org.flightapp.business.AmadeusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(ApiRestController.API)
public class ApiRestController {

    public static final String API = "/api";

    private final AmadeusService amadeusService;

    @GetMapping("/locations")
    public ResponseEntity<Location[]> getLocations(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(amadeusService.getLocation(keyword));
        } catch (ResponseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/confirm")
    public FlightPrice confirmFlightPrice(@RequestBody FlightOfferSearch search) throws ResponseException {
        return amadeusService.confirmFlightPrice(search);
    }





}
