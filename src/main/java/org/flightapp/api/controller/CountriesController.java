package org.flightapp.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.CountriesDTO;
import org.flightapp.api.dto.mapper.CountriesMapper;
import org.flightapp.business.CountriesService;
import org.flightapp.domain.Countries;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/countries")
public class CountriesController {

    private final CountriesService countriesService;
    private final CountriesMapper countriesMapper;

    @GetMapping
    public String countriesPage(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var allCountries = countriesService.findAllCountriesSorted(userName).stream()
                .map(countriesMapper::map).toList();
        model.addAttribute("countries", allCountries);
        return "countries";
    }

    @GetMapping("/countries-add")
    public String showRegistrationForm(Model model) {
        CountriesDTO countryDTO = new CountriesDTO();
        model.addAttribute("countryDTO", countryDTO);
        return "countries-add";
    }

    @PostMapping("/save")
    public String countrySave(
            @Valid @ModelAttribute("countryDTO") CountriesDTO countryDTO,
            BindingResult result
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Countries existingCountry = countriesService.findCountryByCountryNameByUserName(countryDTO.getCountryName(), userName);
        System.out.println("existingCountry: " + existingCountry);
        if (existingCountry != null) {
            result.rejectValue("countryName", "error.countryName", "Państwo o tej nazwie już istnieje");
        }
        if (result.hasErrors()) {
            return "countries-add";
        }
        Countries countryToSave = countriesService.addCountry(countriesMapper.map(countryDTO));
        log.info("Country saved: [country={}]", countryToSave.getCountryName());
        return "redirect:/countries?success";
    }

    @PostMapping("/edit/{countryUUID}")
    public String editCountry(@PathVariable String countryUUID, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        countriesService.changeCountryStatus(countryUUID);
        redirectAttributes.addFlashAttribute("statusMessage", "Status zmieniony pomyślnie");
        return "redirect:/countries";
    }

    @PostMapping("/delete/{countryUUID}")
    public String deleteCountry(@PathVariable String countryUUID, RedirectAttributes redirectAttributes) throws AccessDeniedException {
        Countries countryToDelete = countriesService.findCountryByCountryUUID(countryUUID);
        countriesService.deleteCountry(countryToDelete.getCountryUUID());
        redirectAttributes.addFlashAttribute("deleteMessage", "Państwo usunięte");
        return "redirect:/countries";
    }
}
