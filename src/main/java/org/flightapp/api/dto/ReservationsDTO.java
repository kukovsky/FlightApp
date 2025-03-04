package org.flightapp.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flightapp.domain.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsDTO {

    private Integer reservationId;
    private String departureOrigin;
    private String departureDestination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureReturnDate;
    private String departureAirline;
    private String departureFlightNumber;
    private String returnOrigin;
    private String returnDestination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime returnDepartureDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime returnReturnDate;
    private String returnAirline;
    private String returnFlightNumber;
    private BigDecimal price;
    private String currency;
    private Integer numberOfPassengers;
    private Integer numberOfStops;
    private ReservationStatus status;
}
