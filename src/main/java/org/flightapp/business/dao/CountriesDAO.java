package org.flightapp.business.dao;

import org.flightapp.domain.Countries;

import java.util.List;

public interface CountriesDAO {

    Countries saveCountry(Countries countriesToAdd);

    List<Countries> findAllCountriesSorted(String userName);

    Countries findCountryByCountryUUID(String countryUUID);

    void deleteCountry(String countryUUID);

    List<Countries> findAllCountriesToVisit(String userName);

    Countries findCountryByCountryNameAndUserName(String countryName, String userName);
}
