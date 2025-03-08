package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.AttractionsDTO;
import org.flightapp.business.dao.AttractionsDAO;
import org.flightapp.domain.Attractions;
import org.flightapp.domain.Countries;
import org.flightapp.domain.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AttractionsService {

    private final AttractionsDAO attractionsDAO;
    private final CountriesService countriesService;

    @Transactional
    public Attractions addAttraction(Attractions attraction, String countryUUID) {
        Countries existingCountry = countriesService.findCountryByCountryUUID(countryUUID);
        Attractions attractionsToSave = attraction
                .withAttractionUUID(UUID.randomUUID().toString())
                .withAttractionComment(attraction.getAttractionComment())
                .withAttractionPlace(attraction.getAttractionPlace())
                .withDateTime(attraction.getDateTime())
                .withHourTime(attraction.getHourTime())
                .withCountry(existingCountry);
        log.info("Dodano atrakcję: {}", attractionsToSave);
        return attractionsDAO.saveAttraction(attractionsToSave);
    }

    @Transactional
    public void updateAttraction(AttractionsDTO attractionsDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Attractions attractions = findAttractionByUUID(attractionsDTO.getAttractionUUID());
        String countryUUID = countriesService
                .findCountryByCountryNameByUserName(attractions.getCountry().getCountryName(), userName).getCountryUUID();
        if (!attractions.getCountry().getCountryUUID().equals(countryUUID)) {
            throw new RuntimeException("Atrakcja nie należy do użytkownika");
        }
        Attractions updatedAttraction = attractions
                .withDateTime(attractionsDTO.getDateTime())
                .withHourTime(attractionsDTO.getHourTime())
                .withAttractionPlace(attractionsDTO.getAttractionPlace())
                .withAttractionComment(attractionsDTO.getAttractionComment());
        attractionsDAO.saveAttraction(updatedAttraction);
        log.info("Zaktualizowano atrakcję: {}", updatedAttraction);
    }

    @Transactional
    public void deleteAttraction(String attractionUUID) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Attractions attractions = findAttractionByUUID(attractionUUID);
        String countryUUID = countriesService
                .findCountryByCountryNameByUserName(attractions.getCountry().getCountryName(), userName).getCountryUUID();
        if (!attractions.getCountry().getCountryUUID().equals(countryUUID)) {
            throw new RuntimeException("Atrakcja nie należy do użytkownika");
        }
        attractionsDAO.deleteAttraction(attractions);
        log.info("Usunięto atrakcję: {}", attractions);
    }

    @Transactional
    public Attractions findAttractionByUUID(String attractionUUID) {
        Optional<Attractions> attractions = attractionsDAO.findByAttractionUUID(attractionUUID);
        if (attractions.isEmpty()) {
            throw new NotFoundException("Nie znaleziono atrakcji");
        }
        log.info("Znaleziono atrakcję: {}", attractions.get());
        return attractions.get();
    }

    public List<Attractions> findAllAttractions(String countryUUID) {
        return attractionsDAO.findAllAttractions(countryUUID);
    }


}
