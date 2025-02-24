package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.FlightAppRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<FlightAppRoles, Long> {

    FlightAppRoles findByRole(String name);
}
