package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.ExperienceDTO;
import org.flightapp.domain.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    @Mapping(target = "experienceId", ignore = true)
    @Mapping(target = "user", ignore = true)
    Experience map(ExperienceDTO experienceDTO);

    ExperienceDTO map(Experience experience);
}
