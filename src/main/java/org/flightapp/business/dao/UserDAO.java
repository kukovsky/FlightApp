package org.flightapp.business.dao;

import org.flightapp.domain.User;

public interface UserDAO {

    User saveUser(User user);
    User findByEmail(String email);
    User findByUserName(String userName);
    void saveReservation(User user);
}
