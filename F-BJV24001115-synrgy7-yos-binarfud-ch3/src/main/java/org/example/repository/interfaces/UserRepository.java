package org.example.repository.interfaces;

import org.example.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    boolean add(User user);
    List<User> getAll();
    void deleteById(UUID uuid);
}
