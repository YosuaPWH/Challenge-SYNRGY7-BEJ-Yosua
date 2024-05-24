package org.yosua.binfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yosua.binfood.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByUsername(String username);
}
