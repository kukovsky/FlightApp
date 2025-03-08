package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.UsersEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.jpa.UserJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final ReservationsJpaRepository reservationsJpaRepository;
    private final SourceTargetMapper sourceTargetMapper;
    private JpaContext jpaContext;

    @Override
    public void processReservation(User user) {
        UsersEntity userToSave = sourceTargetMapper.toEntity(user, jpaContext);
        UsersEntity userSaved = userJpaRepository.saveAndFlush(userToSave);

        user.getReservations().stream()
                .filter(reservation -> Objects.isNull(reservation.getReservationId()))
                .map(reservation -> sourceTargetMapper.toEntity(reservation, jpaContext))
                .forEach(reservationsEntity -> {
                    reservationsEntity.setUser(userSaved);
                    reservationsJpaRepository.saveAndFlush(reservationsEntity);
                });
    }

    @Override
    public User saveUser(User user) {
        UsersEntity userToSave = sourceTargetMapper.toEntity(user, jpaContext);
        UsersEntity savedUser = userJpaRepository.save(userToSave);
        return sourceTargetMapper.fromEntity(savedUser, jpaContext);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userEntity -> sourceTargetMapper.fromEntity(userEntity, jpaContext));
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userJpaRepository.findByUserName(userName)
                .map(usersEntity -> sourceTargetMapper.fromEntity(usersEntity, jpaContext));
    }

    @Override
    public Optional<User> findByUserNameWithReservations(String userName) {
        return userJpaRepository.findByUserNameWithReservations(userName)
                .map(usersEntity -> sourceTargetMapper.fromEntity(usersEntity, jpaContext));
    }

}
