package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.ExperienceDAO;
import org.flightapp.domain.Experience;
import org.flightapp.infrastructure.database.entity.ExperienceEntity;
import org.flightapp.infrastructure.database.repository.jpa.ExperienceJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ExperienceRepository implements ExperienceDAO {
    private final SourceTargetMapper sourceTargetMapper;
    private final ExperienceJpaRepository experienceJpaRepository;
    private JpaContext jpaContext;

    @Override
    public Experience saveExperience(Experience experience) {
        ExperienceEntity experienceToSave = sourceTargetMapper.toEntity(experience, jpaContext);
        ExperienceEntity savedExperience = experienceJpaRepository.save(experienceToSave);
        return sourceTargetMapper.fromEntity(savedExperience, jpaContext);
    }

    @Override
    public List<Experience> findAllExperiences(String userName) {
        return experienceJpaRepository.findAllByUserUserName(userName).stream()
                .map(experienceEntity -> sourceTargetMapper.fromEntity(experienceEntity, jpaContext))
                .toList();
    }

    @Override
    public Optional<Experience> findByExperienceUUID(String experienceUUID) {
        return experienceJpaRepository.findByExperienceUUID(experienceUUID)
                .map(experienceEntity -> sourceTargetMapper.fromEntity(experienceEntity, jpaContext));
    }

    @Override
    public void deleteExperience(Experience exisitingExperience) {
        experienceJpaRepository.deleteByExperienceUUID(exisitingExperience.getExperienceUUID());
    }
}
