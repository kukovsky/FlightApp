package org.flightapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"reservationId", "departureFlightNumber", "returnFlightNumber"})
@ToString()
public class Reservations {

    Integer reservationId;
    String departureOrigin;
    String departureDestination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime departureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime departureReturnDate;
    String departureAirline;
    String departureFlightNumber;
    String returnOrigin;
    String returnDestination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime returnDepartureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime returnReturnDate;
    String returnAirline;
    String returnFlightNumber;
    Integer numberOfStops;
    BigDecimal price;
    String currency;
    Integer numberOfPassengers;
    LocalDateTime createdAt;
    ReservationStatus status;
    User user;
}
