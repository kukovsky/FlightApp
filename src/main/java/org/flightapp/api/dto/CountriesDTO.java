package org.flightapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountriesDTO {
    String countryName;
    String countryUUID;
    Boolean visited;
}
