package org.example.flightapp.flightapp;

import org.flightapp.domain.Reservations;
import org.flightapp.infrastructure.database.entity.ReservationsEntity;
import org.flightapp.infrastructure.database.repository.mapper.ReservationsEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReservationsEntityMapperTest {

    private ReservationsEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(ReservationsEntityMapper.class);
    }

    @Test
    public void testMapToEntity() {
        Reservations reservations = Reservations.builder()
                .reservationId(1)
                .origin("NYC")
                .destination("LAX")
                .departureDate(LocalDateTime.of(2023, 10, 1, 10, 0))
                .returnDate(LocalDateTime.of(2023, 10, 10, 10, 0))
                .airline("Delta")
                .flightNumber("DL123")
                .price(new BigDecimal("300.00"))
                .currency("USD")
                .numberOfPassengers(2)
                .createdAt(LocalDateTime.of(2023, 9, 1, 10, 0))
                .build();

        ReservationsEntity entity = mapper.mapToEntity(reservations);

        assertThat(entity).isNotNull();
        assertThat(entity.getReservationId()).isEqualTo(1);
        assertThat(entity.getOrigin()).isEqualTo("NYC");
        assertThat(entity.getDestination()).isEqualTo("LAX");
        assertThat(entity.getDepartureDate()).isEqualTo(LocalDateTime.of(2023, 10, 1, 10, 0));
        assertThat(entity.getReturnDate()).isEqualTo(LocalDateTime.of(2023, 10, 10, 10, 0));
        assertThat(entity.getAirline()).isEqualTo("Delta");
        assertThat(entity.getFlightNumber()).isEqualTo("DL123");
        assertThat(entity.getPrice()).isEqualTo(new BigDecimal("300.00"));
        assertThat(entity.getCurrency()).isEqualTo("USD");
        assertThat(entity.getNumberOfPassengers()).isEqualTo(2);
        assertThat(entity.getCreatedAt()).isEqualTo(LocalDateTime.of(2023, 9, 1, 10, 0));
    }

    @Test
    public void testMapFromEntity() {
        ReservationsEntity entity = ReservationsEntity.builder()
                .reservationId(1)
                .origin("NYC")
                .destination("LAX")
                .departureDate(LocalDateTime.of(2023, 10, 1, 10, 0))
                .returnDate(LocalDateTime.of(2023, 10, 10, 10, 0))
                .airline("Delta")
                .flightNumber("DL123")
                .price(new BigDecimal("300.00"))
                .currency("USD")
                .numberOfPassengers(2)
                .createdAt(LocalDateTime.of(2023, 9, 1, 10, 0))
                .build();

        Reservations reservations = mapper.mapFromEntity(entity);

        assertThat(reservations).isNotNull();
        assertThat(reservations.getReservationId()).isEqualTo(1);
        assertThat(reservations.getOrigin()).isEqualTo("NYC");
        assertThat(reservations.getDestination()).isEqualTo("LAX");
        assertThat(reservations.getDepartureDate()).isEqualTo(LocalDateTime.of(2023, 10, 1, 10, 0));
        assertThat(reservations.getReturnDate()).isEqualTo(LocalDateTime.of(2023, 10, 10, 10, 0));
        assertThat(reservations.getAirline()).isEqualTo("Delta");
        assertThat(reservations.getFlightNumber()).isEqualTo("DL123");
        assertThat(reservations.getPrice()).isEqualTo(new BigDecimal("300.00"));
        assertThat(reservations.getCurrency()).isEqualTo("USD");
        assertThat(reservations.getNumberOfPassengers()).isEqualTo(2);
        assertThat(reservations.getCreatedAt()).isEqualTo(LocalDateTime.of(2023, 9, 1, 10, 0));
    }
}