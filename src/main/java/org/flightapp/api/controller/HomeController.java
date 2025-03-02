package org.flightapp.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.flightapp.api.dto.UserDTO;
import org.flightapp.api.dto.mapper.UsersMapper;
import org.flightapp.business.UserService;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/index")
    public String homePage() {
        return "index";
    }

}
