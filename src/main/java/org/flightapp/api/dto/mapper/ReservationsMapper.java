package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.domain.Reservations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationsMapper {

    ReservationsDTO map(final Reservations reservations);

    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user.userId", ignore = true)
    @Mapping(target = "user.createdAt", ignore = true)
    @Mapping(target = "user.active", ignore = true)
    @Mapping(target = "user.roles", ignore = true)
    Reservations map(ReservationsDTO reservationsDTO);
}
