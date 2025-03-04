package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SourceTargetMapper {

    UsersEntity toEntity(User s);

    @Mapping(target = "user", ignore = true)
    ReservationsEntity toEntity(Reservations s);

    @Mapping(source = "reservations", target = "reservations", qualifiedByName = "mapReservations")
    User fromEntity(UsersEntity s);

    @Named("mapReservations")
    default Set<Reservations> mapInvoices(Set<ReservationsEntity> invoiceEntities) {
        return invoiceEntities.stream().map(this::fromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "user", ignore = true)
    Reservations fromEntity(ReservationsEntity s);
}
