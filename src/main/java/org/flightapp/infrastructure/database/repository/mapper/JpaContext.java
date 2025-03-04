package org.flightapp.infrastructure.database.repository.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaContext {

    private final EntityManager em;

    private UsersEntity usersEntity;

    @BeforeMapping
    public void setEntity(@MappingTarget UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @AfterMapping
    public void establishRelation(@MappingTarget ReservationsEntity reservationsEntity) {
         reservationsEntity.setUser(usersEntity);
        }
    }

