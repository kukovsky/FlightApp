package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.UserDTO;
import org.flightapp.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {


    User map(UserDTO userDTO);

    UserDTO map(User user);

}
