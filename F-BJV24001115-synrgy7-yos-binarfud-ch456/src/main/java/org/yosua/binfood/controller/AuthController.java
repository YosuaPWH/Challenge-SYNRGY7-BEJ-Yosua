package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.LoginUserRequest;
import org.yosua.binfood.model.dto.request.RefreshTokenRequest;
import org.yosua.binfood.model.dto.request.RegisterUserRequest;
import org.yosua.binfood.model.dto.response.ApiResponse;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.services.AuthService;
import org.yosua.binfood.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
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

    @PostMapping("/refreshToken")
    public ApiResponse<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        JwtResponse tokenResponse = refreshTokenService.refreshToken(request);
        return ApiResponse.<JwtResponse>builder()
                .success(true)
                .data(tokenResponse)
                .build();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
