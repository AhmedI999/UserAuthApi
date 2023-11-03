package com.userapi.userapi.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    // response of retrieving the user
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean marketingConsent;
}
