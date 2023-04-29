package eu.balev.security.service.impl;

import eu.balev.security.model.entity.UserEntity;
import eu.balev.security.model.entity.UserRoleEntity;
import eu.balev.security.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public AppUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

    return userRepository.
        findByUserName(userName).
        map(this::map).
        orElseThrow(() -> new UsernameNotFoundException("User with email " + userName + " not found."));
  }

  private UserDetails map(UserEntity userEntity) {

    return new User(
        userEntity.getUserName(),
        userEntity.getPassword(),
        asGrantedAuthorities(userEntity.getRoles())
    );
  }

  private List<GrantedAuthority> asGrantedAuthorities(List<UserRoleEntity> roleEntities) {
    return roleEntities.
        stream().
        map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).
        collect(Collectors.toList());
  }
}
