package org.flightapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionsDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourTime;
    private String attractionPlace;
    private String attractionComment;
    private String attractionUUID;
}
