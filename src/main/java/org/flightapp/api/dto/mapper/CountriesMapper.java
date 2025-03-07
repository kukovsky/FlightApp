package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.CountriesDTO;
import org.flightapp.domain.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountriesMapper {

    @Mapping(target = "countryId", ignore = true)
    @Mapping(target = "user", ignore = true)
    Countries map(CountriesDTO countriesDTO);

    CountriesDTO map(Countries countries);

}
