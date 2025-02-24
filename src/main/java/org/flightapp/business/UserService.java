package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.business.dao.UserDAO;
import org.flightapp.domain.User;
import org.flightapp.domain.exception.NotFoundException;
import org.flightapp.infrastructure.database.entity.FlightAppRoles;
import org.flightapp.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {


    private final RoleJpaRepository roleJpaRepository;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void save(User user) {
        FlightAppRoles role = roleJpaRepository.findByRole("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user = user.withPassword(passwordEncoder.encode(user.getPassword()))
                .withRoles(Set.of(role))
                .withActive(true)
                .withCreatedAt(LocalDateTime.now());
        log.info("User saved: [userName={}, email={}, roles={}]", user.getUserName(), user.getEmail(), user.getRoles());
        userDAO.saveUser(user);

    }

    @Transactional
    public User findUserByEmail(String email) {
        Optional<User> user = userDAO.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User with email: [%s] not found".formatted(email));
        }
        return user.get();
    }

    @Transactional
    public User findUserByUserName(String userName) {
        Optional<User> user = userDAO.findByUserName(userName);
        if (user.isEmpty()) {
            throw new NotFoundException("User with userName: [%s] not found".formatted(userName));
        }
        return user.get();
    }

    private FlightAppRoles checkRoleExist() {
        FlightAppRoles role = new FlightAppRoles();
        role.setRole("ROLE_ADMIN");
        return roleJpaRepository.save(role);
    }


}
