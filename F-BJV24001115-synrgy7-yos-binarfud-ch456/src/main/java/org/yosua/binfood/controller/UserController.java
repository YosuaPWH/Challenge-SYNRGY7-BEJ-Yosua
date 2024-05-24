package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yosua.binfood.model.request.RegisterUserMerchantRequest;
import org.yosua.binfood.model.response.ApiResponse;
import org.yosua.binfood.model.response.UserResponse;
import org.yosua.binfood.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserResponse> register(@RequestBody RegisterUserMerchantRequest request) {
        UserResponse userResponse = userService.register(request);
        return ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .build();
    }

}
