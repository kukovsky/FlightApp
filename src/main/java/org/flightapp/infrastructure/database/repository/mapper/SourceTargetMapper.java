package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SourceTargetMapper {

    @Mapping(target = "reservations", ignore = true)
    UsersEntity toEntity(User s, @Context JpaContext ctx);

    ReservationsEntity toEntity(Reservations s, @Context JpaContext ctx);

    @Mapping(target = "reservations", ignore = true)
    User fromEntity(UsersEntity s, @Context JpaContext ctx);

    Reservations fromEntity(ReservationsEntity s, @Context JpaContext ctx);
}

