package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.UserDTO;
import org.flightapp.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {

    UserDTO map(final User user);

    User map(UserDTO userDTO);
}
