package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.flightapp.api.dto.AttractionsDTO;
import org.flightapp.business.dao.AttractionsDAO;
import org.flightapp.domain.Attractions;
import org.flightapp.domain.Countries;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
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
        System.out.println("in method addAtrraction service:" + attractionsToSave);
        return attractionsDAO.saveAttraction(attractionsToSave);
    }

    @Transactional
    public void updateAttraction(AttractionsDTO attractionsDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Attractions attractions = attractionsDAO.findByAttractionUUID(attractionsDTO.getAttractionUUID());
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
    }

    @Transactional
    public void deleteAttraction(String attractionUUID) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Attractions attractions = attractionsDAO.findByAttractionUUID(attractionUUID);
        String countryUUID = countriesService
                .findCountryByCountryNameByUserName(attractions.getCountry().getCountryName(), userName).getCountryUUID();
        if (!attractions.getCountry().getCountryUUID().equals(countryUUID)) {
            throw new RuntimeException("Atrakcja nie należy do użytkownika");
        }
        attractionsDAO.deleteAttraction(attractions);
    }

    public List<Attractions> findAllAttractions(String countryUUID) {
        return attractionsDAO.findAllAttractions(countryUUID);
    }

    public Attractions findAttractionByUUID(String attractionUUID) {
        return attractionsDAO.findByAttractionUUID(attractionUUID);
    }


}
