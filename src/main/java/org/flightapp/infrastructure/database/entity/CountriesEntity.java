package org.flightapp.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "countryId")
@ToString(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_countries")
public class CountriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_name", nullable = false, length = 50)
    private String countryName;

    @Column(name = "country_UUID", nullable = false, length = 50, unique = true)
    private String countryUUID;

    @Column(name = "visited", nullable = false)
    private Boolean visited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<AttractionsEntity> attractions;
}
