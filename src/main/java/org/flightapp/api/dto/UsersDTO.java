package org.flightapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    String firstName;
    String lastName;
    String email;
    Set<ReservationsDTO> reservations;
}
