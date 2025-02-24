package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.flightapp.infrastructure.database.repository.jpa.UserJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.UsersEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UsersEntityMapper usersEntityMapper;


    @Override
    public void saveUser(User user) {
        FlightAppUsersEntity userToSave = usersEntityMapper.mapToEntity(user);
        FlightAppUsersEntity savedUser = userJpaRepository.save(userToSave);
        usersEntityMapper.mapFromEntity(savedUser);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(usersEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userJpaRepository.findByUserName(userName)
                .map(usersEntityMapper::mapFromEntity);
    }





}
