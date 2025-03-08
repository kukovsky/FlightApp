package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienceJpaRepository extends JpaRepository<ExperienceEntity, Integer> {

    List<ExperienceEntity> findAllByUserUserName(String userName);

    Optional<ExperienceEntity> findByExperienceUUID(String experienceUUID);

    void deleteByExperienceUUID(String experienceUUID);
}
