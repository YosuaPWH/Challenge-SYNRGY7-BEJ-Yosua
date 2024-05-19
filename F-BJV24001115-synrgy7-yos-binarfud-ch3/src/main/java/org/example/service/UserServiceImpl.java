package org.example.service;

import org.example.model.User;
import org.example.model.dto.AddUserRequest;
import org.example.repository.interfaces.UserRepository;
import org.example.repository.UserRepositoryImpl;
import org.example.service.interfaces.UserService;
import org.example.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static UserService userService;
    private UserRepository userRepository;

    private UserServiceImpl() {
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public boolean add(AddUserRequest addUserRequest) {
        boolean isExistByUsername = userRepository.getAll().stream()
                .anyMatch(u -> u.getUsername().equals(addUserRequest.getUsername()));

        boolean isExistByEmail = userRepository.getAll().stream()
                .anyMatch(u -> u.getEmail().equals(addUserRequest.getEmail()));

        if (isExistByUsername || isExistByEmail) {
            return false;
        }
        return userRepository.add(addUserRequest);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void deleteById(String id) {
        if (!Utils.isValidUUID(id)) {
            throw new IllegalArgumentException("Invalid UUID");
        }

        UUID uuid = UUID.fromString(id);
        userRepository.deleteById(uuid);
    }

    @Override
    public Optional<User> findByID(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
