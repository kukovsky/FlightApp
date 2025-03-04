package org.flightapp.api.dto.mapper;

import jakarta.persistence.EntityManager;
import org.flightapp.domain.Reservations;
import org.flightapp.domain.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;


public class JpaContext {

    private final EntityManager em;

    private User user;

    public JpaContext(EntityManager em) {
        this.em = em;
    }

    @BeforeMapping
    public void setEntity(@MappingTarget User user) {
        this.user = user;
    }

    @AfterMapping
    public Reservations establishRelation(@MappingTarget Reservations reservations) {
        reservations = reservations.withUser(user);
        return reservations;
    }

}