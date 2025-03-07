package org.flightapp.api.controller;

import lombok.AllArgsConstructor;
import org.flightapp.business.AttractionsService;
import org.flightapp.business.CountriesService;
import org.flightapp.business.ExperienceService;
import org.flightapp.domain.Countries;
import org.flightapp.domain.Experience;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ProgressController {

    private final CountriesService countriesService;
    private final ExperienceService experienceService;
    private final AttractionsService attractionsService;

    @GetMapping("/progress")
    public String travelPage(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var allCountries = countriesService.findAllCountriesSorted(userName);
        var allExperiences = experienceService.findAllExperiences(userName);

        Integer totalCountries = allCountries.size();
        Integer visitedCountries = (int) allCountries.stream().filter(Countries::getVisited).count();
        Integer totalExperiences = allExperiences.size();
        Integer completedExperiences = (int) allExperiences.stream().filter(Experience::getDone).count();

        Map<String, Integer> attractionsCountMap = new HashMap<>();
        for (Countries country : allCountries) {
            Integer attractionsCount = attractionsService.findAllAttractions(country.getCountryUUID()).size();
            attractionsCountMap.put(country.getCountryUUID(), attractionsCount);
        }

        double totalProgress = (totalCountries + totalExperiences) > 0
                ? Math.round(((visitedCountries + completedExperiences) * 100.0 / (totalCountries + totalExperiences)) * 100.0) / 100.0
                : 0.0;
        var countriesToVisit = countriesService.findAllCountriesToVisit(userName);
        model.addAttribute("visitedCountries", visitedCountries);
        model.addAttribute("totalCountries", totalCountries);
        model.addAttribute("completedExperiences", completedExperiences);
        model.addAttribute("totalExperiences", totalExperiences);
        model.addAttribute("progressPercentage", totalProgress);
        model.addAttribute("countriesToVisit", countriesToVisit);
        model.addAttribute("attractionsCountMap", attractionsCountMap);
        return "progress";
    }

}
