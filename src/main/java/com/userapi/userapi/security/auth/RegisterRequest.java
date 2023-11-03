package com.userapi.userapi.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    // register fields
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean marketingConsent;
}
