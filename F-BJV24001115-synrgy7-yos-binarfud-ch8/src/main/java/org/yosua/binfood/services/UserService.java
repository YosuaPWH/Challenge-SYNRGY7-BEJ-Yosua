package org.yosua.binfood.services;

import org.yosua.binfood.model.dto.request.ChangePasswordRequest;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.model.entity.User;

import java.util.Optional;

public interface UserService {
    UserResponse authenticatedUser();
    Optional<User> getUserByEmail(String email);
    void changePassword(ChangePasswordRequest request);
    void delete(String id);
}
