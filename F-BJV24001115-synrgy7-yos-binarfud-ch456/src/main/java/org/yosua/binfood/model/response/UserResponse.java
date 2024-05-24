package org.yosua.binfood.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UserResponse {
    private UUID id;
    private String emailAddress;
    private String username;
}
