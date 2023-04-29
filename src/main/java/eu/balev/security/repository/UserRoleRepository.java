package eu.balev.security.repository;

import eu.balev.security.model.entity.UserRoleEntity;
import eu.balev.security.model.enums.UserRoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository
    extends JpaRepository<UserRoleEntity, Long> {

  Optional<UserRoleEntity> findByRole(UserRoleEnum role);

}
