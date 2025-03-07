package org.flightapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.ExperienceDTO;
import org.flightapp.business.dao.ExperienceDAO;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.Experience;
import org.flightapp.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ExperienceService {

    private final UserDAO userDAO;
    private final ExperienceDAO experienceDAO;

    @Transactional
    public Experience addExperience(Experience experience) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userDAO.findByUserName(userName);
        Experience experienceToSave = experience
                .withExperienceUUID(UUID.randomUUID().toString())
                .withExperienceComment(experience.getExperienceComment())
                .withUser(existingUser)
                .withDone(false);
        return experienceDAO.saveExperience(experienceToSave);
    }

    @Transactional
    public void changeExperienceStatus(String experienceUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience exisitingExperience = experienceDAO.findByExperienceUUID(experienceUUID);
        if (!exisitingExperience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego doświadczenia");
        }
        Experience updatedExperience = exisitingExperience.withDone(!exisitingExperience.getDone());
        experienceDAO.saveExperience(updatedExperience);
    }

    @Transactional
    public void deleteExperience(String experienceUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience exisitingExperience = experienceDAO.findByExperienceUUID(experienceUUID);
        if (!exisitingExperience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do usunięcia tego doświadczenia");
        }
        experienceDAO.deleteExperience(exisitingExperience);

    }
    @Transactional
    public void updateExperience(ExperienceDTO experienceDTO) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience experience = experienceDAO.findByExperienceUUID(experienceDTO.getExperienceUUID());
        if (!experience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego doświadczenia");
        }
        Experience updatedExperience = experience.withExperienceComment(experienceDTO.getExperienceComment());
        experienceDAO.saveExperience(updatedExperience);

    }

    public List<Experience> findAllExperiences(String userName) {
        return experienceDAO.findAllExperiences(userName);
    }

    public Experience findExperienceByUUID(String experienceUUID) {
        return experienceDAO.findByExperienceUUID(experienceUUID);
    }
}
