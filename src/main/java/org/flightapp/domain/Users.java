package org.flightapp.domain;


import lombok.*;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;

import java.time.Instant;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"userId", "firstName", "lastName", "email", "role", "createdTime"})
public class Users {

    Integer userId;
    String firstName;
    String lastName;
    String email;
    String passwordHash;
    String role;
    Instant createdTime;
    Set<ReservationsEntity> reservations;
}
