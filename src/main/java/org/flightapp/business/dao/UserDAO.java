package org.flightapp.business.dao;

import org.flightapp.domain.User;

import java.util.Optional;

public interface UserDAO {

    void saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
}
