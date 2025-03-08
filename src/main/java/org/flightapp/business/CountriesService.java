package org.flightapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.CountriesDAO;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.Countries;
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
public class CountriesService {

    private final UserDAO userDAO;
    private final CountriesDAO countriesDAO;

    @Transactional
    public void addCountry(Countries countries) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> existingUser = userDAO.findByUserName(userName);
        if (existingUser.isPresent()) {
            log.info("Znaleziono użytkownika: {}", existingUser);
        } else {
            throw new NotFoundException("Użytkownik o podanym identyfikatorze nie istnieje");
        }
        Countries countriesToAdd = countries
                .withCountryUUID(UUID.randomUUID().toString())
                .withCountryName(countries.getCountryName())
                .withUser(existingUser.get())
                .withVisited(countries.getVisited());
        countriesDAO.saveCountry(countriesToAdd);
        log.info("Dodano kraj: {}", countriesToAdd);
    }

    @Transactional
    public void deleteCountry(String countryUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Countries existingCountry = findCountryByCountryUUID(countryUUID);
        if (!existingCountry.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego kraju");
        }
        countriesDAO.deleteCountry(countryUUID);
        log.info("Usunięto kraj: {}", existingCountry);
    }

    @Transactional
    public void changeCountryStatus(String countryUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Countries existingCountry = findCountryByCountryUUID(countryUUID);
        if (!existingCountry.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego kraju");
        }
        Countries updatedCountry = existingCountry.withVisited(!existingCountry.getVisited());
        countriesDAO.saveCountry(updatedCountry);
        log.info("Zmieniono status kraju: {}", updatedCountry);
    }

    @Transactional
    public Countries findCountryByCountryUUID(String countryUUID) {
        Optional<Countries> country = countriesDAO.findCountryByCountryUUID(countryUUID);
        if (country.isEmpty()) {
            throw new NotFoundException("Kraj o podanym identyfikatorze nie istnieje");
        }
        log.info("Znaleziono kraj: {}", country);
        return country.get();
    }

    @Transactional
    public Countries findCountryByCountryNameByUserName(String countryName, String userName) {
        Optional<Countries> country = countriesDAO.findCountryByCountryNameAndUserName(countryName, userName);
        log.info("Znaleziono kraj: {}", country);
        return country.orElse(null);
    }

    public List<Countries> findAllCountriesSorted(String userName) {
        return countriesDAO.findAllCountriesSorted(userName);
    }

    public List<Countries> findAllCountriesToVisit(String userName) {
        return countriesDAO.findAllCountriesToVisit(userName);
    }
}
