package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.ChangePasswordRequest;
import org.yosua.binfood.model.dto.request.UpdateUserRequest;
import org.yosua.binfood.model.dto.response.ApiResponse;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.services.impl.UserServiceImpl;
import org.yosua.binfood.utils.Constants;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(
            path = "/me",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserResponse> authenticatedUser() {
        UserResponse userResponse = userService.authenticatedUser();
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userResponse)
                .message(Constants.GET_AUTHENTICATED_USER_SUCCESS)
                .build();
    }

    @PostMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> update(@RequestBody UpdateUserRequest request) {
        userService.update(request);
        return ApiResponse.<String>builder()
                .success(true)
                .data(null)
                .message(Constants.UPDATE_USER_SUCCESS)
                .build();
    }

    @PostMapping(
            path = "/change_password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.<String>builder()
                .success(true)
                .data(null)
                .message(Constants.CHANGE_PASSWORD_SUCCESS)
                .build();
    }

    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ApiResponse.<String>builder()
                .success(true)
                .data(null)
                .message(Constants.DELETE_USER_SUCCESS)
                .build();
    }
}
