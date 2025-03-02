package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SourceTargetMapper {

    FlightAppUsersEntity toEntity(User s, @Context JpaContext ctx);

    @Mapping(target = "user", ignore = true)
    ReservationsEntity toEntity(Reservations s, @Context JpaContext ctx);

    User fromEntity(FlightAppUsersEntity s, @Context JpaContext ctx);

    @Mapping(target = "user", ignore = true)
    Reservations fromEntity(ReservationsEntity s, @Context JpaContext ctx);
}
