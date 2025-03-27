package org.flightapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.ExperienceDTO;
import org.flightapp.business.dao.ExperienceDAO;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.Experience;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
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
        Optional<User> existingUser = userDAO.findByUserName(userName);
        if (existingUser.isPresent()) {
            log.info("Znaleziono użytkownika o nazwie: {}", existingUser.get().getUserName());
        } else {
            throw new NotFoundException("Nie znaleziono użytkownika o podanej nazwie");
        }
        Experience experienceToSave = experience
                .withExperienceUUID(UUID.randomUUID().toString())
                .withExperienceComment(experience.getExperienceComment())
                .withUser(existingUser.get())
                .withDone(false);
        return experienceDAO.saveExperience(experienceToSave);
    }

    @Transactional
    public void changeExperienceStatus(String experienceUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience exisitingExperience = findExperienceByUUID(experienceUUID);
        if (!exisitingExperience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego wyzwania");
        }
        Experience updatedExperience = exisitingExperience.withDone(!exisitingExperience.getDone());
        experienceDAO.saveExperience(updatedExperience);
    }

    @Transactional
    public void deleteExperience(String experienceUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience exisitingExperience = findExperienceByUUID(experienceUUID);
        if (!exisitingExperience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do usunięcia tego wyzwania");
        }
        experienceDAO.deleteExperience(exisitingExperience);

    }
    @Transactional
    public void updateExperience(ExperienceDTO experienceDTO) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Experience experience = findExperienceByUUID(experienceDTO.getExperienceUUID());
        if (!experience.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego wyzwania");
        }
        Experience updatedExperience = experience.withExperienceComment(experienceDTO.getExperienceComment());
        experienceDAO.saveExperience(updatedExperience);
        log.info("Zaktualizowano wyzwanie o identyfikatorze: {}", experience.getExperienceUUID());

    }

    @Transactional
    public Experience findExperienceByUUID(String experienceUUID) {
        Optional<Experience> experience = experienceDAO.findByExperienceUUID(experienceUUID);
        if (experience.isEmpty()) {
            throw new NotFoundException("Nie znaleziono wyzwania o podanym identyfikatorze");
        }
        log.info("Znaleziono wyzwanie o identyfikatorze: {}", experience.get().getExperienceUUID());
        return experience.get();
    }

    public List<Experience> findAllExperiences(String userName) {
        return experienceDAO.findAllExperiences(userName);
    }
}
