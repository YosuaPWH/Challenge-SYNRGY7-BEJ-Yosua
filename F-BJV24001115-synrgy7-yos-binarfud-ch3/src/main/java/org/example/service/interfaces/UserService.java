package org.example.service.interfaces;

import org.example.model.User;
import org.example.model.dto.AddUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(AddUserRequest addUserRequest);
    List<User> getAll();
    void deleteById(String id);
    Optional<User> findByID(String id);
    Optional<User> findByUsername(String username);
}
