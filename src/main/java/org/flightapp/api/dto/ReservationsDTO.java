package org.flightapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsDTO {
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private String airline;
    private Integer numberOfPassengers;
    private String price;
    private String currency;
}
