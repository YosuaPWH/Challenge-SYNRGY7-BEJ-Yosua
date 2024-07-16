package org.yosua.binfood.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class UserResponse {
    private UUID id;
    private String email;
    private String fullName;
    private List<String> roles;
}
