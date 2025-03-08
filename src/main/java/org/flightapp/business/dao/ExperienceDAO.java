package org.flightapp.business.dao;

import org.flightapp.domain.Experience;

import java.util.List;
import java.util.Optional;

public interface ExperienceDAO {

    Experience saveExperience(Experience experienceToSave);

    List<Experience> findAllExperiences(String userName);

    Optional<Experience> findByExperienceUUID(String experienceUUID);

    void deleteExperience(Experience exisitingExperience);
}
