package org.example.repository;

import org.example.model.User;
import org.example.repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository userRepository;
    private final ArrayList<User> users;

    public UserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }

    @Override
    public boolean add(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        return users.add(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void deleteById(UUID uuid) {
        users.stream()
                .filter(user -> user.getId().equals(uuid))
                .findFirst()
                .ifPresent(users::remove);
    }
}
