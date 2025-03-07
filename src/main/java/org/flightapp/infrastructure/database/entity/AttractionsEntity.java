package org.flightapp.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode(of = "attractionId")
@ToString(exclude = {"country"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attractions")
public class AttractionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Integer attractionId;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "hour_time")
    private LocalTime hourTime;

    @Column(name = "attraction_place")
    private String attractionPlace;

    @Column(name = "attraction_comment")
    private String attractionComment;

    @Column(name = "attractionUUID")
    private String attractionUUID;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountriesEntity country;
}
