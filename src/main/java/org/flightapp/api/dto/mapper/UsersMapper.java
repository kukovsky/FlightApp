package org.flightapp.api.dto.mapper;

import org.flightapp.api.dto.UsersDTO;
import org.flightapp.domain.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersDTO map(Users users);
}
