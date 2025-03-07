package org.flightapp.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"countryUUID"})
@ToString(exclude = "user")
public class Countries {
    Integer countryId;
    String countryName;
    String countryUUID;
    Boolean visited;
    User user;
}
