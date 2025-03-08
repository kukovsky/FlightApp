package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.CountriesDAO;
import org.flightapp.domain.Countries;
import org.flightapp.infrastructure.database.entity.CountriesEntity;
import org.flightapp.infrastructure.database.repository.jpa.CountriesJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CountriesRepository implements CountriesDAO {

    private final SourceTargetMapper sourceTargetMapper;
    private final JpaContext jpaContext;
    private final CountriesJpaRepository countriesJpaRepository;

    @Override
    public void saveCountry(Countries country) {
        CountriesEntity countriesToSave = sourceTargetMapper.toEntity(country, jpaContext);
        CountriesEntity savedCountry = countriesJpaRepository.save(countriesToSave);
        sourceTargetMapper.fromEntity(savedCountry, jpaContext);
    }

    @Override
    public void deleteCountry(String countryUUID) {
        countriesJpaRepository.deleteByCountryUUID(countryUUID);
    }

    @Override
    public Optional<Countries> findCountryByCountryUUID(String countryUUID) {
        return countriesJpaRepository.findByCountryUUID(countryUUID)
                .map(countriesEntity -> sourceTargetMapper.fromEntity(countriesEntity, jpaContext));
    }

    @Override
    public Optional<Countries> findCountryByCountryNameAndUserName(String countryName, String userName) {
        return countriesJpaRepository.findByCountryNameAndUserUserName(countryName, userName)
                .map(countriesEntity -> sourceTargetMapper.fromEntity(countriesEntity, jpaContext));
    }

    @Override
    public List<Countries> findAllCountriesToVisit(String userName) {
        return countriesJpaRepository.findAllByUserUserNameAndVisitedFalse(userName).stream()
                .map(countriesEntity -> sourceTargetMapper.fromEntity(countriesEntity, jpaContext))
                .toList();
    }

    @Override
    public List<Countries> findAllCountriesSorted(String userName) {
        return countriesJpaRepository.findAllByUserUserNameOrderByCountryNameAsc(userName).stream()
                .map(countriesEntity -> sourceTargetMapper.fromEntity(countriesEntity, jpaContext))
                .toList();
    }


}
