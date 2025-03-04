package org.flightapp.domain;


import lombok.*;
import org.flightapp.infrastructure.database.entity.UserRoles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"userName", "email"})
@ToString(exclude = {"password", "roles", "reservations"})
public class User {

    Integer userId;
    String userName;
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDateTime createdAt;
    Boolean active;
    Set<UserRoles> roles;
    Set<Reservations> reservations;

    public Set<Reservations> getReservations() {
        return Objects.isNull(reservations) ? new HashSet<>() : reservations;
    }
}
