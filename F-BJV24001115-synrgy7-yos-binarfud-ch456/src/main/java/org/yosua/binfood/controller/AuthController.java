package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.request.LoginUserMerchantRequest;
import org.yosua.binfood.model.response.ApiResponse;
import org.yosua.binfood.model.response.TokenResponse;
import org.yosua.binfood.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<TokenResponse> login(@RequestBody LoginUserMerchantRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return ApiResponse.<TokenResponse>builder()
                .data(tokenResponse)
                .build();
    }

    @PostMapping(
            path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> logout(User user) {
        authService.logout(user);
        return ApiResponse.<String>builder()
                .data("Logout success")
                .build();
    }

}
