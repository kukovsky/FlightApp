package org.flightapp.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Size(min = 6, message = "Hasło musi mieć co najmniej 6 znaków")
    String password;
}
