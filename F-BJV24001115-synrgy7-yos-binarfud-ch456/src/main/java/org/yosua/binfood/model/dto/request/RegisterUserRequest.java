package org.yosua.binfood.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    @Email(message = "Email format is incorrect")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Name cannot be empty")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    private String location;
}
