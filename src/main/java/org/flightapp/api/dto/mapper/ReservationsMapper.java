package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.domain.Reservations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationsMapper {

    @Mapping(target = "user")
    ReservationsDTO map(Reservations reservations);

    @Mapping(target = "user")
    Reservations map(ReservationsDTO reservationsDTO);

}
