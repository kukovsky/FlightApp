package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceJpaRepository extends JpaRepository<ExperienceEntity, Integer> {

    List<ExperienceEntity> findAllByUserUserName(String userName);

    ExperienceEntity findByExperienceUUID(String experienceUUID);

    void deleteByExperienceUUID(String experienceUUID);
}
