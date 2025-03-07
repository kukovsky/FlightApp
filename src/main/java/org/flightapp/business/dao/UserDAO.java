package org.flightapp.business.dao;

import org.flightapp.domain.User;

public interface UserDAO {

    User saveUser(User user);
    User findByEmail(String email);
    User findByUserNameWithReservations(String userName);
    void processReservation(User user);
    User findByUserName(String userName);
}
