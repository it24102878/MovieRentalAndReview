package com.moviesphere.controller;

import com.moviesphere.model.Authentication;
import com.moviesphere.model.PasswordAuth;
import com.moviesphere.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody PasswordAuth auth) {
        try {
            authService.createAuth(auth);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody PasswordAuth auth) {
        try {
            boolean isValid = authService.verifyAuth(auth.getEmail(), auth.getPassword());
            if (isValid) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAuth(@RequestBody PasswordAuth auth) {
        try {
            authService.updateAuth(auth);
            return ResponseEntity.ok("Credentials updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteAuth(@PathVariable String email) {
        try {
            authService.deleteAuth(email);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestBody PasswordAuth auth) {
        try {
            authService.updateAuth(auth); // Simplified for demo
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}