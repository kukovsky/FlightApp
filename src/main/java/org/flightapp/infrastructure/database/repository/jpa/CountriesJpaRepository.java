package org.flightapp.infrastructure.database.repository.jpa;

import org.flightapp.infrastructure.database.entity.CountriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountriesJpaRepository extends JpaRepository<CountriesEntity, Integer> {

    List<CountriesEntity> findAllByUserUserNameOrderByCountryNameAsc(String userName);

    CountriesEntity findByCountryUUID(String countryUUID);

    void deleteByCountryUUID(String countryUUID);

    List<CountriesEntity> findAllByUserUserNameAndVisitedFalse(String userName);

    CountriesEntity findByCountryNameAndUserUserName(String countryName, String userName);
}
