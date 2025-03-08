package org.flightapp.business.dao;

import org.flightapp.domain.User;

import java.util.Optional;

public interface UserDAO {

    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameWithReservations(String userName);
    void processReservation(User user);
    Optional<User> findByUserName(String userName);
}
