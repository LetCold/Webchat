package com.phamthainguyen.webchat.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private byte gender;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;
}
