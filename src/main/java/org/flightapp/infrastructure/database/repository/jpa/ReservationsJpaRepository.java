package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationsJpaRepository extends JpaRepository<ReservationsEntity, Long> {

    List<ReservationsEntity> findByUser_UserId(Integer userUserId);
}
