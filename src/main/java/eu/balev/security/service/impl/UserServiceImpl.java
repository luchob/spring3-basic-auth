package eu.balev.security.service.impl;

import static eu.balev.security.model.enums.UserRoleEnum.EMPLOYEE;
import static eu.balev.security.model.enums.UserRoleEnum.MANAGER;

import eu.balev.security.model.entity.UserEntity;
import eu.balev.security.model.entity.UserRoleEntity;
import eu.balev.security.repository.UserRepository;
import eu.balev.security.repository.UserRoleRepository;
import eu.balev.security.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRoleRepository userRoleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final String defaultPassword;

  public UserServiceImpl(
      UserRoleRepository userRoleRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      @Value("${app.default.password}") String defaultPassword) {
    this.userRoleRepository = userRoleRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.defaultPassword = defaultPassword;
  }

  @Override
  public void initUserRoles() {
    if (userRoleRepository.count() == 0) {
      UserRoleEntity employeeRole = new UserRoleEntity().setRole(EMPLOYEE);
      UserRoleEntity managerRole = new UserRoleEntity().setRole(MANAGER);

      userRoleRepository.saveAll(List.of(employeeRole, managerRole));
    }
  }

  @Override
  public void initUsers() {
    if (userRepository.count() == 0) {
      UserEntity managerUser = new UserEntity().
          setFirstName("manager").
          setLastName("manager").
          setUserName("manager").
          setPassword(passwordEncoder.encode(defaultPassword)).
          setRoles(userRoleRepository.findAll());

      UserRoleEntity employeeRole = userRoleRepository.findByRole(EMPLOYEE).
          orElseThrow(() -> new IllegalStateException("Roles are not initialized properly."));

      UserEntity employeeUser = new UserEntity().
          setFirstName("employee").
          setLastName("employee").
          setUserName("employee").
          setPassword(passwordEncoder.encode(defaultPassword)).
          setRoles(List.of(employeeRole));

      userRepository.saveAll(List.of(managerUser, employeeUser));
    }
  }
}
