package org.flightapp.infrastructure.database.repository.mapper;

import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationsEntityMapper {

    ReservationsEntity mapToEntity(ReservationsEntity entity);
}
