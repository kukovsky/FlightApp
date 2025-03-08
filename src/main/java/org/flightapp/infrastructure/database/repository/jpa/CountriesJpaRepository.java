package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.CountriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountriesJpaRepository extends JpaRepository<CountriesEntity, Integer> {

    List<CountriesEntity> findAllByUserUserNameOrderByCountryNameAsc(String userName);

    Optional<CountriesEntity> findByCountryUUID(String countryUUID);

    void deleteByCountryUUID(String countryUUID);

    List<CountriesEntity> findAllByUserUserNameAndVisitedFalse(String userName);

    Optional<CountriesEntity> findByCountryNameAndUserUserName(String countryName, String userName);
}
