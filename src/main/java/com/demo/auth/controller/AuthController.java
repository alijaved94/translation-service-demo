package com.demo.auth.controller;

import com.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Generates a JWT token for authentication.
     * This is a temporary endpoint for testing.
     * In a production environment, you’d implement a proper authentication mechanism (e.g., username/password login)
     * to issue tokens securely.
     *
     * @return ResponseEntity containing the generated JWT token.
     */
    @GetMapping("/token")
    public ResponseEntity<String> generateToken() {
        return ResponseEntity.ok( authService.generateToken() );
    }
}
