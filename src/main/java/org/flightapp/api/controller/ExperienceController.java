package org.flightapp.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.ExperienceDTO;
import org.flightapp.api.dto.mapper.ExperienceMapper;
import org.flightapp.business.ExperienceService;
import org.flightapp.domain.Experience;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ExperienceMapper experienceMapper;

    @GetMapping
    public String experiencePage(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var allExperiences = experienceService.findAllExperiences(userName).stream()
                .map(experienceMapper::map).toList();
        model.addAttribute("experiences", allExperiences);
        return "experiences";
    }

    @GetMapping("/experience-add")
    public String experienceAddPage(Model model) {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        model.addAttribute("experienceDTO", experienceDTO);
        return "experience-add";
    }

    @GetMapping("/edit/{experienceUUID}")
    public String experienceEditPage(@PathVariable String experienceUUID, Model model) {
        ExperienceDTO experienceDTO = experienceMapper.map(experienceService.findExperienceByUUID(experienceUUID));
        model.addAttribute("experienceDTO", experienceDTO);
        return "experience-edit";
    }

    @PostMapping("/update")
    public String updateExperience(@ModelAttribute("experienceDTO") ExperienceDTO experienceDTO, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        experienceService.updateExperience(experienceDTO);
        redirectAttributes.addFlashAttribute("statusMessage", "Doświadczenie zaktualizowane");
        return "redirect:/experiences";
    }

    @PostMapping("/save")
    public String experienceSave(
            @Valid @ModelAttribute("experienceDTO") ExperienceDTO experienceDTO) {
        Experience experience = experienceService.addExperience(experienceMapper.map(experienceDTO));
        log.info("Experience added: {}", experience);
        return "redirect:/experiences?success";
    }

    @PutMapping("/edit-status/{experienceUUID}")
    public String experienceEditStatus(@PathVariable String experienceUUID, Model model, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        experienceService.changeExperienceStatus(experienceUUID);
        redirectAttributes.addFlashAttribute("statusMessage", "Status doświadczenia został zmieniony");
        model.addAttribute("experienceUUID", experienceUUID);
        return "redirect:/experiences";
    }

    @DeleteMapping("/delete/{experienceUUID}")
    public String experienceDelete(@PathVariable String experienceUUID, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        experienceService.deleteExperience(experienceUUID);
        redirectAttributes.addFlashAttribute("deleteMessage", "Doświadczenie zostało usunięte");
        return "redirect:/experiences";
    }
}
