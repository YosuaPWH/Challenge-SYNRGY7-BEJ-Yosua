package org.yosua.binfood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yosua.binfood.model.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String>{
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
