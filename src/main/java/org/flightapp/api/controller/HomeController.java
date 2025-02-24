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

    private UserService userService;
    private UsersMapper usersMapper;

    @GetMapping("/index")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult result,
            Model model
    ) {
        try {
            User userEmailExists = userService.findUserByEmail(userDTO.getEmail());
            if (userEmailExists != null) {
                result.rejectValue("email", "email exists", "There is already an account registered with that email");
            }
        } catch (NotFoundException e) {
            // User email not found, proceed with registration
        }

        try {
            User userNameExists = userService.findUserByUserName(userDTO.getUserName());
            if (userNameExists != null) {
                result.rejectValue("userName", "username exists", "There is already an account registered with that username");
            }
        } catch (NotFoundException e) {
            // Username not found, proceed with registration
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/register";
        }

        User user = usersMapper.map(userDTO);
        userService.save(user);

        return "redirect:/register?success";

    }

    @GetMapping("/flights")
    public String users(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        model.addAttribute("user", usersMapper.map(user));
        return "flights";
    }


}
