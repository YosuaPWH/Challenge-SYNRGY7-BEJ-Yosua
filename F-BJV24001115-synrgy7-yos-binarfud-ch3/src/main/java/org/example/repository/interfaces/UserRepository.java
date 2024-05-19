package org.example.repository.interfaces;

import org.example.model.User;
import org.example.model.dto.AddUserRequest;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    boolean add(AddUserRequest addUserRequest);
    List<User> getAll();
    boolean deleteById(UUID uuid);
    User getById(UUID uuid);
    boolean isExistByID(UUID uuid);
    boolean isExistByEmail(String email);
    boolean isExistByUserName(String userName);
}
