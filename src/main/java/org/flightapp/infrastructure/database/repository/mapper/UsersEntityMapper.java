package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.domain.Users;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersEntityMapper {

    @Mapping(target = "reservations", ignore = true)
    Users mapFromEntity(UsersEntity entity);

}
