package org.flightapp.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"attractionUUID"})
@ToString(exclude = "country")

public class Attractions {

    Integer attractionId;
    LocalDate dateTime;
    LocalTime hourTime;
    String attractionPlace;
    String attractionComment;
    String attractionUUID;
    Countries country;


}
