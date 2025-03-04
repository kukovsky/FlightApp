package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.jpa.UserJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final SourceTargetMapper sourceTargetMapper;
    private final ReservationsJpaRepository reservationsJpaRepository;


    @Override
    public void saveReservation(User user) {
        UsersEntity userToSave = sourceTargetMapper.toEntity(user);
        UsersEntity userSaved = userJpaRepository.saveAndFlush(userToSave);

        user.getReservations().stream()
                .filter(reservation -> Objects.isNull(reservation.getReservationId()))
                .map(sourceTargetMapper::toEntity)
                .forEach(reservationsEntity -> {
                    reservationsEntity.setUser(userSaved);
                    reservationsJpaRepository.saveAndFlush(reservationsEntity);
                });
    }

    @Override
    public User saveUser(User user) {
        UsersEntity userToSave = sourceTargetMapper.toEntity(user);
        UsersEntity savedUser = userJpaRepository.save(userToSave);
        return sourceTargetMapper.fromEntity(savedUser);
    }


    @Override
    public User findByEmail(String email) {
        UsersEntity userEntity = userJpaRepository.findByEmail(email);
        return sourceTargetMapper.fromEntity(userEntity);
    }

    @Override
    public User findByUserName(String userName) {
        UsersEntity usersEntity = userJpaRepository.findByUserName(userName);
        return sourceTargetMapper.fromEntity(usersEntity);
    }

}
