package org.flightapp.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.flightapp.domain.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"reservationId"})
@ToString(of = {"reservationId", "departureOrigin", "departureDestination", "departureDate",
        "departureReturnDate", "departureAirline", "departureFlightNumber",
        "returnOrigin", "returnDestination", "returnDepartureDate", "returnReturnDate",
        "returnAirline", "returnFlightNumber", "price", "currency", "numberOfPassengers", "status", "createdAt"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class ReservationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Integer reservationId;

    @Column(name = "departure_origin", nullable = false, length = 3)
    private String departureOrigin;

    @Column(name = "departure_destination", nullable = false, length = 3)
    private String departureDestination;

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "departure_return_date")
    private LocalDateTime departureReturnDate;

    @Column(name = "departure_airline", nullable = false, length = 10)
    private String departureAirline;

    @Column(name = "departure_flight_number", nullable = false, length = 10)
    private String departureFlightNumber;

    @Column(name = "return_origin", length = 3)
    private String returnOrigin;

    @Column(name = "return_destination", length = 3)
    private String returnDestination;

    @Column(name = "return_departure_date")
    private LocalDateTime returnDepartureDate;

    @Column(name = "return_return_date")
    private LocalDateTime returnReturnDate;

    @Column(name = "return_airline", length = 10)
    private String returnAirline;

    @Column(name = "return_flight_number", length = 10)
    private String returnFlightNumber;

    @Column(name = "number_of_stops")
    private Integer numberOfStops;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "number_of_passengers", nullable = false)
    private Integer numberOfPassengers;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private FlightAppUsersEntity user;
}