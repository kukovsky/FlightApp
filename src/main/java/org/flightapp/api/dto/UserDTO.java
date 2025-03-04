package org.flightapp.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    String userName;
    String firstName;
    String lastName;
    @Email
    String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password;
    Set<ReservationsDTO> reservations;

}
