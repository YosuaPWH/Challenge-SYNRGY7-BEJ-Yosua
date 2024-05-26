package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yosua.binfood.model.request.LoginUserRequest;
import org.yosua.binfood.model.request.RegisterUserRequest;
import org.yosua.binfood.model.response.ApiResponse;
import org.yosua.binfood.model.response.JwtResponse;
import org.yosua.binfood.model.response.UserResponse;
import org.yosua.binfood.services.AuthService;
import org.yosua.binfood.services.LogoutService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final LogoutService logoutService;

    @Autowired
    public AuthController(AuthService authService, LogoutService logoutService) {
        this.authService = authService;
        this.logoutService = logoutService;
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserResponse> register(@RequestBody RegisterUserRequest request) {
        UserResponse userResponse = authService.register(request);
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userResponse)
                .build();
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<JwtResponse> login(@RequestBody LoginUserRequest request) {
        JwtResponse tokenResponse = authService.login(request);
        return ApiResponse.<JwtResponse>builder()
                .success(true)
                .data(tokenResponse)
                .build();
    }

    @PostMapping(
            path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> logout() {
        return ApiResponse.<String>builder()
                .success(true)
                .data("Logout success")
                .build();
    }

}
