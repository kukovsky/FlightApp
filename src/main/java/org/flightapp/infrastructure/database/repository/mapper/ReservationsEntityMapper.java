package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.Reservations;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationsEntityMapper {

    @Mapping(target = "user.userId")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "departureDate", source = "departureDate")
    @Mapping(target = "returnDate", source = "returnDate")
    ReservationsEntity mapToEntity(Reservations entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "departureDate", source = "departureDate")
    @Mapping(target = "returnDate", source = "returnDate")
    Reservations mapFromEntity(ReservationsEntity entity);

}
