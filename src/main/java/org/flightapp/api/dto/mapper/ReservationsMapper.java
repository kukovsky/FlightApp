package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.domain.Reservations;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationsMapper {

    ReservationsDTO map(final Reservations reservations);

    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Reservations map(ReservationsDTO reservationsDTO);
}
