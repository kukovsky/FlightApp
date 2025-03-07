package org.flightapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDTO {
    String experienceComment;
    String experienceUUID;
    Boolean done;

}
