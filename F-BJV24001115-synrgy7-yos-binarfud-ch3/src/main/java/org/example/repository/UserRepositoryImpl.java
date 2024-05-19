package org.example.repository;

import org.example.model.User;
import org.example.model.dto.AddUserRequest;
import org.example.repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository userRepository;
    private final ArrayList<User> users;

    private UserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }

    @Override
    public boolean add(AddUserRequest addUserRequest) {
        User user = new User(
                UUID.randomUUID(),
                addUserRequest.getUsername(),
                addUserRequest.getEmail(),
                addUserRequest.getPassword()
        );

        return users.add(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public boolean deleteById(UUID uuid) {
        if (!isExistByID(uuid)) {
            throw new IllegalArgumentException("User with id " + uuid + " not found");
        }
        return users.removeIf(user -> user.getId().equals(uuid));
    }

    @Override
    public User getById(UUID uuid) {
        return users.stream()
                .filter(user -> user.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isExistByID(UUID uuid) {
        return users.stream()
                .anyMatch(user -> user.getId().equals(uuid));
    }

    @Override
    public boolean isExistByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean isExistByUserName(String userName) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(userName));
    }
}
