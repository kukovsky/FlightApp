package org.flightapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.CountriesDAO;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.Countries;
import org.flightapp.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CountriesService {

    private final UserDAO userDAO;
    private final CountriesDAO countriesDAO;

    @Transactional
    public Countries addCountry(Countries countries) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userDAO.findByUserName(userName);
        Countries countriesToAdd = countries
                .withCountryUUID(UUID.randomUUID().toString())
                .withCountryName(countries.getCountryName())
                .withUser(existingUser)
                .withVisited(countries.getVisited());
        return countriesDAO.saveCountry(countriesToAdd);
    }

    @Transactional
    public void deleteCountry(String countryUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Countries existingCountry = countriesDAO.findCountryByCountryUUID(countryUUID);
        if (!existingCountry.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego kraju");
        }
        countriesDAO.deleteCountry(countryUUID);
    }

    @Transactional
    public void changeCountryStatus(String countryUUID) throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Countries existingCountry = countriesDAO.findCountryByCountryUUID(countryUUID);
        if (!existingCountry.getUser().getUserName().equals(userName)) {
            throw new AccessDeniedException("Nie masz uprawnień do edycji tego kraju");
        }
        Countries updatedCountry = existingCountry.withVisited(!existingCountry.getVisited());
        countriesDAO.saveCountry(updatedCountry);
    }

    public Countries findCountryByCountryUUID(String countryUUID) {
        return countriesDAO.findCountryByCountryUUID(countryUUID);
    }

    public List<Countries> findAllCountriesSorted(String userName) {
        return countriesDAO.findAllCountriesSorted(userName);
    }

    public List<Countries> findAllCountriesToVisit(String userName) {
        return countriesDAO.findAllCountriesToVisit(userName);
    }

    public Countries findCountryByCountryNameByUserName(String countryName, String userName) {
        return countriesDAO.findCountryByCountryNameAndUserName(countryName, userName);
    }
}
