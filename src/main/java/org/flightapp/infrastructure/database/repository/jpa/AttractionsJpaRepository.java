package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.AttractionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttractionsJpaRepository extends JpaRepository<AttractionsEntity, Integer> {


    Optional<AttractionsEntity> findByAttractionUUID(String attractionUUID);

    void deleteByAttractionUUID(String attractionUUID);

    List<AttractionsEntity> findAllByCountryCountryUUID(String countryUUID);
}
