package com.phamthainguyen.webchat.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    private String fullName;

    private byte gender;

    private String email;

    private String password;
}