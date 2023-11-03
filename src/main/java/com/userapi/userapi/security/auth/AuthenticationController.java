package com.userapi.userapi.security.auth;

import com.userapi.userapi.entity.User;
import com.userapi.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserRepository repository;

    // To register and authenticate a new user
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(service.register(request));
    }
    // To be used in case there is a user in the database and is not authenticated
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    // Retriving user details
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@RequestBody AuthenticationResponse response,
                                                @PathVariable String id) {
        User user = repository.findById(id).orElseThrow();
        return ResponseEntity.ok(service.retrieveUser(response, user));
    }
}
