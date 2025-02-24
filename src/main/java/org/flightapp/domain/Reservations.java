package org.flightapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@With
@Builder
@EqualsAndHashCode(of = "reservationId")
@ToString(of = {"reservationId", "origin", "destination", "departureDate", "returnDate", "airline", "price", "currency", "numberOfPassengers", "createdAt"})
public class Reservations {

    Integer reservationId;
    String origin;
    String destination;
    String flightNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime departureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime returnDate;
    String airline;
    BigDecimal price;
    String currency;
    Integer numberOfPassengers;
    LocalDateTime createdAt;
    User user;
}
