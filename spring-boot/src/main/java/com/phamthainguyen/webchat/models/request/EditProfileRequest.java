package com.phamthainguyen.webchat.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest {
    @NotBlank
    private String fullName;
    @NotBlank
    private String imageName;
    
}
