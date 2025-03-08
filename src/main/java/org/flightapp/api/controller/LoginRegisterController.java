package org.flightapp.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.UserDTO;
import org.flightapp.api.dto.mapper.UsersMapper;
import org.flightapp.business.UserService;
import org.flightapp.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginRegisterController {

    private UserService userService;
    private UsersMapper usersMapper;

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
        User existingByUserName = userService.findUserByUserNameWithReservations(userDTO.getUserName());
        User existingByEmail = userService.findUserByEmail(userDTO.getEmail());
        if (existingByUserName != null) {
            result.rejectValue("userName", "error.user", "Nazwa użytkownika jest już zajęta");
        }
        if (existingByEmail != null) {
            result.rejectValue("email", "error.user", "Adres email jest już zajęty");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/register";
        }
        userService.save(usersMapper.map(userDTO));
        return "redirect:/register?success";
    }
}
