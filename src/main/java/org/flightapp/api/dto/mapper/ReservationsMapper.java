package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.ReservationsDTO;
import org.flightapp.domain.Reservations;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationsMapper {

    ReservationsDTO map(Reservations reservations);
}
