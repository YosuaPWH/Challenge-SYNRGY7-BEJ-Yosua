package org.yosua.binfood.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String email;
    private List<String> roles;
    @JsonProperty("jwt_token")
    private String jwtToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
