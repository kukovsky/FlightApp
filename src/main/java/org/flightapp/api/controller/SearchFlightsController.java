package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SearchFlightsController {

    @GetMapping("/flights")
    public String users() {
        return "flights";
    }


}
