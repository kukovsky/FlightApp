package org.flightapp.business.dao;

import org.flightapp.domain.Countries;

import java.util.List;
import java.util.Optional;

public interface CountriesDAO {

    void saveCountry(Countries countriesToAdd);

    List<Countries> findAllCountriesSorted(String userName);

    Optional<Countries> findCountryByCountryUUID(String countryUUID);

    void deleteCountry(String countryUUID);

    List<Countries> findAllCountriesToVisit(String userName);

    Optional<Countries> findCountryByCountryNameAndUserName(String countryName, String userName);
}
