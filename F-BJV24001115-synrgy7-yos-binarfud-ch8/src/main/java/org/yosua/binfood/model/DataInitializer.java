package org.yosua.binfood.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yosua.binfood.model.entity.Role;
import org.yosua.binfood.repositories.RoleRepository;
import org.yosua.binfood.utils.Roles;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<Role> roles = Arrays.asList(
                Role.builder().name(Roles.ROLE_USER.toString()).build(),
                Role.builder().name(Roles.ROLE_MERCHANT.toString()).build()
        );

        roles.forEach(role -> {
            if (!roleRepository.existsByName(role.getName())) {
                roleRepository.save(role);
            }
        });
    }
}
