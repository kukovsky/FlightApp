package org.flightapp.api.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.AttractionsDTO;
import org.flightapp.api.dto.mapper.AttractionMapper;
import org.flightapp.business.AttractionsService;
import org.flightapp.business.CountriesService;
import org.flightapp.domain.Attractions;
import org.flightapp.domain.Countries;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final AttractionMapper attractionMapper;
    private final AttractionsService attractionsService;
    private final CountriesService countriesService;

    @GetMapping("/{countryUUID}")
    public String travel(@PathVariable String countryUUID, Model model) {
        var allAttractions = attractionsService.findAllAttractions(countryUUID).stream()
                .map(attractionMapper::map).toList();
        Countries countryName = countriesService.findCountryByCountryUUID(countryUUID);
        model.addAttribute("countryName", countryName.getCountryName());
        model.addAttribute("attractions", allAttractions);
        return "travel";
    }

    @GetMapping("/{countryUUID}/attraction-add")
    public String attractionAddPage(@PathVariable String countryUUID, Model model) {
        AttractionsDTO attractionsDTO = new AttractionsDTO();
        model.addAttribute("attractionsDTO", attractionsDTO);
        model.addAttribute("countryUUID", countryUUID);
        return "attraction-add";
    }

    @GetMapping("/{countryUUID}/attraction-edit/{attractionUUID}")
    public String attractionEditPage(@PathVariable String countryUUID, @PathVariable String attractionUUID, Model model) {
        AttractionsDTO attractionsDTO = attractionMapper.map(attractionsService.findAttractionByUUID(attractionUUID));
        model.addAttribute("attractionsDTO", attractionsDTO);
        model.addAttribute("countryUUID", countryUUID);
        return "attraction-edit";
    }

    @PostMapping("/{countryUUID}/save")
    public String saveAttraction(@PathVariable String countryUUID, @ModelAttribute AttractionsDTO attractionsDTO, Model model) {
        Attractions attractions = attractionsService.addAttraction(attractionMapper.map(attractionsDTO), countryUUID);
        log.info("Attraction added: {}", attractions);
        model.addAttribute("countryUUID", countryUUID);
        model.addAttribute("succes", true);
        return "redirect:/travel/" + countryUUID;
    }

    @PostMapping("/{countryUUID}/update")
    public String updateAttraction(
            @PathVariable String countryUUID,
            @ModelAttribute AttractionsDTO attractionsDTO,
            Model model ,
            RedirectAttributes redirectAttributes
    ) {
        attractionsService.updateAttraction(attractionsDTO);
        model.addAttribute("countryUUID", countryUUID);
        redirectAttributes.addFlashAttribute("statusMessage", "Atrakcja zaktualizowana!");
        return "redirect:/travel/" + countryUUID;
    }

    @PostMapping("/{countryUUID}/delete/{attractionUUID}")
    public String deleteAttraction(@PathVariable String countryUUID,
                                   @PathVariable String attractionUUID,
                                   Model model
            ,RedirectAttributes redirectAttributes
    ) {
        attractionsService.deleteAttraction(attractionUUID);
        model.addAttribute("countryUUID", countryUUID);
        redirectAttributes.addFlashAttribute("deleteMessage", "Atrakcja usunięta!");
        return "redirect:/travel/" + countryUUID;
    }
}
