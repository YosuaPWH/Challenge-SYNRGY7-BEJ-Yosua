package org.yosua.binfood.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank
    @JsonProperty("new_password")
    private String newPassword;
}
