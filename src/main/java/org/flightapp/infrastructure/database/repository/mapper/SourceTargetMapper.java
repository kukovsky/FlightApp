package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.*;
import org.flightapp.infrastructure.database.entity.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SourceTargetMapper {

    @Mapping(target = "reservations", ignore = true)
    UsersEntity toEntity(User s, @Context JpaContext ctx);

    ReservationsEntity toEntity(Reservations s, @Context JpaContext ctx);

    @Mapping(target = "reservations", ignore = true)
    User fromEntity(UsersEntity s, @Context JpaContext ctx);

    Reservations fromEntity(ReservationsEntity s, @Context JpaContext ctx);

    CountriesEntity toEntity(Countries s, @Context JpaContext ctx);

    Countries fromEntity(CountriesEntity s, @Context JpaContext ctx);

    ExperienceEntity toEntity(Experience ex, @Context JpaContext ctx);

    Experience fromEntity(ExperienceEntity ex, @Context JpaContext ctx);

    AttractionsEntity toEntity(Attractions s, @Context JpaContext ctx);

    Attractions fromEntity(AttractionsEntity s, @Context JpaContext ctx);
}

