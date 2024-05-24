package org.yosua.binfood.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserMerchantRequest {
    @NotBlank
    private String emailAddress;

    @NotBlank
    private String password;
}
