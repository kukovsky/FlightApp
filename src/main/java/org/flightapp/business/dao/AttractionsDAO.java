package org.flightapp.business.dao;

import org.flightapp.domain.Attractions;

import java.util.List;

public interface AttractionsDAO {
    Attractions saveAttraction(Attractions attractionsToSave);

    Attractions findByAttractionUUID(String attractionUUID);

    void deleteAttraction(Attractions attractions);

    List<Attractions> findAllAttractions(String countryUUID);
}
