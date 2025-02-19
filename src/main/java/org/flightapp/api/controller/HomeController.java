package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@AllArgsConstructor
@RequestMapping(value= HomeController.HOME)
public class HomeController {

    static final String HOME = "/home";

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("message", "Hello from FlightApp!");
        model.addAttribute("items", Arrays.asList("item1", "item2", "item3"));
        return "home";
    }


}
