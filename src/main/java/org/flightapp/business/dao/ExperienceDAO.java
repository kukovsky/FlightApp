package org.flightapp.business.dao;

import org.flightapp.domain.Experience;

import java.util.List;

public interface ExperienceDAO {

    Experience saveExperience(Experience experienceToSave);

    List<Experience> findAllExperiences(String userName);

    Experience findByExperienceUUID(String experienceUUID);

    void deleteExperience(Experience exisitingExperience);
}
