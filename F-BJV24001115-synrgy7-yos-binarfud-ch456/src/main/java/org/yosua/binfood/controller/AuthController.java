package org.yosua.binfood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.LoginUserRequest;
import org.yosua.binfood.model.dto.request.RefreshTokenRequest;
import org.yosua.binfood.model.dto.request.RegisterUserRequest;
import org.yosua.binfood.model.dto.response.BaseResponse;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.services.AuthService;
import org.yosua.binfood.services.TokenService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthController {

    private final AuthService authService;
    private final TokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Register a new user with email, full name, password, and location",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User successfully registered"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse userResponse = authService.register(request);

        BaseResponse<UserResponse> response = BaseResponse.<UserResponse>builder()
                .success(true)
                .data(userResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@Valid @RequestBody LoginUserRequest request) {
        JwtResponse tokenResponse = authService.login(request);

        BaseResponse<JwtResponse> response = BaseResponse.<JwtResponse>builder()
                .success(true)
                .data(tokenResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout() {
        BaseResponse<String> response = BaseResponse.<String>builder()
                .success(true)
                .data("Logout success")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<JwtResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        JwtResponse tokenResponse = refreshTokenService.refreshToken(request);

        BaseResponse<JwtResponse> response = BaseResponse.<JwtResponse>builder()
                .success(true)
                .data(tokenResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
