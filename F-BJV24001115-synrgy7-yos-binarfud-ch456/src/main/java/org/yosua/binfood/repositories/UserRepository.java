package org.yosua.binfood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yosua.binfood.model.entity.Role;
import org.yosua.binfood.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByEmailAddress(String emailAddress);
    Optional<User> findFirstByUsername(String username);
    Optional<User> findFirstByToken(String token);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByUsername(String username);
    Optional<User> findAllByRoles(List<Role> roles);
}
