package org.flightapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    WAITING_FOR_PAYMENT("Oczekuje na płatność"),
    PAID("Opłacona"),
    CANCELLED("Anulowana");

    private final String status;


}
