package org.flightapp.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.FlightAppUsersEntity;
import org.flightapp.infrastructure.database.repository.jpa.ReservationsJpaRepository;
import org.flightapp.infrastructure.database.repository.jpa.UserJpaRepository;
import org.flightapp.infrastructure.database.repository.mapper.JpaContext;
import org.flightapp.infrastructure.database.repository.mapper.SourceTargetMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final SourceTargetMapper sourceTargetMapper;
    private final ReservationsJpaRepository reservationsJpaRepository;

    JpaContext jpaCtx = new JpaContext(null);


    @Override
    public void saveReservation(User user) {
        FlightAppUsersEntity userToSave = sourceTargetMapper.toEntity(user, jpaCtx);
        FlightAppUsersEntity userSaved = userJpaRepository.saveAndFlush(userToSave);

        user.getReservations().stream()
                .filter(reservation -> Objects.isNull(reservation.getReservationId()))
                .map(reservation -> sourceTargetMapper.toEntity(reservation, jpaCtx))
                .forEach(reservationsEntity -> {
                    reservationsEntity.setUser(userSaved);
                    reservationsJpaRepository.saveAndFlush(reservationsEntity);
                });
    }

    @Override
    public User saveUser(User user) {
        FlightAppUsersEntity userToSave = sourceTargetMapper.toEntity(user, jpaCtx);
        FlightAppUsersEntity savedUser = userJpaRepository.save(userToSave);
        return sourceTargetMapper.fromEntity(savedUser, jpaCtx);
    }


    @Override
    public User findByEmail(String email) {
        FlightAppUsersEntity userEntity = userJpaRepository.findByEmail(email);
        return sourceTargetMapper.fromEntity(userEntity, jpaCtx);
    }

    @Override
    public User findByUserName(String userName) {
        FlightAppUsersEntity usersEntity = userJpaRepository.findByUserName(userName);
        return sourceTargetMapper.fromEntity(usersEntity, jpaCtx);
    }

}
