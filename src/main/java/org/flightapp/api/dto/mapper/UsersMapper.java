package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.UserDTO;
import org.flightapp.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReservationsMapper.class)
public interface UsersMapper {

    UserDTO map(final User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    User map(UserDTO userDTO);
}
