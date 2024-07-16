package org.yosua.binfood.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String email;
    private List<String> roles;
    private String jwtToken;
    private String refreshToken;
}
