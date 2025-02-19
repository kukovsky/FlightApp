package org.flightapp.domain;

import lombok.*;
import org.flightapp.infrastructure.database.entity.UsersEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
@Value
@With
@Builder
@EqualsAndHashCode(of = "reservationId")
@ToString(of = {"reservationId", "origin", "destination", "departureDate", "returnDate", "airline", "price", "currency", "numberOfPassengers", "status", "createdAt"})
public class Reservations {

    Integer reservationId;
    String origin;
    String destination;
    LocalDate departureDate;
    LocalDate returnDate;
    String airline;
    BigDecimal price;
    String currency;
    Integer numberOfPassengers;
    String status;
    Instant createdAt;
    UsersEntity user;
}
