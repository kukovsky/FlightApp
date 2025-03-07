package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.AttractionsDTO;
import org.flightapp.domain.Attractions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttractionMapper {

    AttractionsDTO map(Attractions attractions);

    @Mapping(target = "attractionId", ignore = true)
    @Mapping(target = "country", ignore = true)
    Attractions map(AttractionsDTO attractionsDTO);
}
