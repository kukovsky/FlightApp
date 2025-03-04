package org.flightapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flightapp.api.dto.mapper.UsersMapper;
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
    private final UsersMapper usersMapper;


    @Transactional
    public void saveReservation(User user) {
        userDAO.saveReservation(user);
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
        User user = userDAO.findByEmail(email);
//        if (user == null) {
//            throw new NotFoundException("User with email: [%s] not found".formatted(email));
//        }
        return user;
    }

    @Transactional
    public User findUserByUserName(String userName) {
        User user = userDAO.findByUserName(userName);
//        if (user == null) {
//            throw new NotFoundException("User with userName: [%s] not found".formatted(userName));
//        }
        return user;
    }


    private UserRoles checkRoleExist() {
        UserRoles role = new UserRoles();
        role.setRole("ROLE_ADMIN");
        return roleJpaRepository.save(role);
    }


}
