package org.flightapp.business.dao;

import org.flightapp.domain.Attractions;

import java.util.List;
import java.util.Optional;

public interface AttractionsDAO {
    Attractions saveAttraction(Attractions attractionsToSave);

    Optional<Attractions> findByAttractionUUID(String attractionUUID);

    void deleteAttraction(Attractions attractions);

    List<Attractions> findAllAttractions(String countryUUID);
}
