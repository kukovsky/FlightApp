package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.infrastructure.database.entity.UserRoles;
import org.flightapp.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {


    private final RoleJpaRepository roleJpaRepository;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void saveReservation(User user) {
        userDAO.processReservation(user);
    }


    @Transactional
    public User save(User user) {
        UserRoles role = roleJpaRepository.findByRole("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        User userToSave = user.withPassword(passwordEncoder.encode(user.getPassword()))
                .withRoles(Set.of(role))
                .withCreatedAt(LocalDateTime.now())
                .withActive(true);
        log.info("User saved: [userName={}, email={}, roles={}]", user.getUserName(), user.getEmail(), user.getRoles());
        return userDAO.saveUser(userToSave);
    }

    @Transactional
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Transactional
    public User findUserByUserNameWithReservations(String userName) {
        return userDAO.findByUserNameWithReservations(userName);
    }


    private UserRoles checkRoleExist() {
        UserRoles role = new UserRoles();
        role.setRole("ROLE_ADMIN");
        return roleJpaRepository.save(role);
    }


}
