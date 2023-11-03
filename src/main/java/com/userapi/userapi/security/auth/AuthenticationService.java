package com.userapi.userapi.security.auth;

import com.userapi.userapi.entity.Role;
import com.userapi.userapi.entity.User;
import com.userapi.userapi.repository.UserRepository;
import com.userapi.userapi.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Register builder, Saving the User to the database and creating token build
    public AuthenticationResponse register(RegisterRequest request) throws NoSuchAlgorithmException {
        String id = hashEmail(request.getEmail());
        var user = User.builder()
                .id(id)
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .marketingConsent(request.isMarketingConsent())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(id)
                .token(jwtToken)
                .build();
    }
    // To be used in case there is a user in the database and is not authenticated
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword())
        );
        var user = userRepository.findById(request.getId()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .token(jwtToken)
                .build();
    }
    // Retrieve User after confirming that the token is valid
    public UserResponse retrieveUser(@RequestBody AuthenticationResponse response, User user) {
        UserResponse authedUser = null;
        if (jwtService.isTokenValid(response.getToken(), user)) {
            authedUser = UserResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.isMarketingConsent() ? user.getEmail() : "Hidden per user request")
                    .marketingConsent(user.isMarketingConsent())
                    .build();
        }
        return authedUser;
    }
    // Hasing email address with SHA-1 and salting with a String
    public String hashEmail (String email) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String salt = "450d0b0db2bcf4adde5032eca1a7c416e560cf44";
        String saltedString = email + salt;
        byte[] hashInBytes = md.digest(saltedString.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
