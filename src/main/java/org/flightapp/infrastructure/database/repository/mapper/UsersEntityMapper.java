package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersEntityMapper {

    @Mapping(target = "reservations", ignore = true)
    User mapFromEntity(FlightAppUsersEntity entity);

    @Mapping(target = "reservations", ignore = true)
    FlightAppUsersEntity mapToEntity(User user);
}
