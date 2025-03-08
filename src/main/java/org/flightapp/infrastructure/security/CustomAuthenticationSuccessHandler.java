package org.flightapp.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Zalogowano jako: {}", username);
        log.info("Role użytkownika: {}", auth.getAuthorities());


        request.getSession().setAttribute("username", username);

        try {
            response.sendRedirect("/flightapp/flights");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
