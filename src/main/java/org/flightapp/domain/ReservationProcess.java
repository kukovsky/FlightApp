package org.flightapp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "processId")
@ToString(exclude = {"reservation"})
public class ReservationProcess {

    Integer processId;
    ReservationsEntity reservation;
    ReservationStatus status;

}
