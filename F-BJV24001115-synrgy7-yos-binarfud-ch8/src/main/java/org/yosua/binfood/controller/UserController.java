package org.yosua.binfood.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.ChangePasswordRequest;
import org.yosua.binfood.model.dto.response.BaseResponse;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.services.UserService;
import org.yosua.binfood.services.impl.UserServiceImpl;
import org.yosua.binfood.utils.Constants;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> authenticatedUser() {
        UserResponse userResponse = userService.authenticatedUser();

        BaseResponse<UserResponse> response = BaseResponse.<UserResponse>builder()
                .success(true)
                .message(Constants.GET_AUTHENTICATED_USER_SUCCESS)
                .data(userResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change_password")
    public ResponseEntity<BaseResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);

        BaseResponse<String> response = BaseResponse.<String>builder()
                .success(true)
                .message(Constants.CHANGE_PASSWORD_SUCCESS)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable("id") String id) {
        userService.delete(id);

        BaseResponse<String> response = BaseResponse.<String>builder()
                .success(true)
                .message(Constants.DELETE_USER_SUCCESS)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
