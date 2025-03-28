package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.AttractionsDAO;
import org.flightapp.domain.Attractions;
import org.flightapp.infrastructure.database.entity.AttractionsEntity;
import org.flightapp.infrastructure.database.repository.jpa.AttractionsJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AttractionsRepository implements AttractionsDAO {


    private final SourceTargetMapper sourceTargetMapper;
    private final AttractionsJpaRepository attractionsJpaRepository;
    private JpaContext jpaContext;

    @Override
    public Attractions saveAttraction(Attractions attraction) {
        AttractionsEntity attractionToSave = sourceTargetMapper.toEntity(attraction, jpaContext);
        AttractionsEntity savedAttraction = attractionsJpaRepository.save(attractionToSave);
        return sourceTargetMapper.fromEntity(savedAttraction, jpaContext);
    }

    @Override
    public Optional<Attractions> findByAttractionUUID(String attractionUUID) {
            return attractionsJpaRepository.findByAttractionUUID(attractionUUID)
                    .map(attractionsEntity -> sourceTargetMapper.fromEntity(attractionsEntity, jpaContext));
    }

    @Override
    public void deleteAttraction(Attractions attractions) {
        attractionsJpaRepository.deleteByAttractionUUID(attractions.getAttractionUUID());
    }

    @Override
    public List<Attractions> findAllAttractions(String countryUUID) {
        return attractionsJpaRepository.findAllByCountryCountryUUID(countryUUID).stream()
                .map(attractionsEntity -> sourceTargetMapper.fromEntity(attractionsEntity, jpaContext))
                .toList();
    }

}
