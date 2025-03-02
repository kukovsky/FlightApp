package org.flightapp.infrastructure.database.repository.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaContext {

    private final EntityManager em;

    private FlightAppUsersEntity flightAppUsersEntity;

    @BeforeMapping
    public void setEntity(@MappingTarget FlightAppUsersEntity usersEntity) {
        this.flightAppUsersEntity = usersEntity;
    }

    @AfterMapping
    public void establishRelation(@MappingTarget ReservationsEntity reservationsEntity) {
         reservationsEntity.setUser(flightAppUsersEntity);
        }
    }

