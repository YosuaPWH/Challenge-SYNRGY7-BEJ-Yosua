package org.yosua.binfood.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "Current password cannot be empty")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank(message = "New password cannot be empty")
    @JsonProperty("new_password")
    private String newPassword;
}
