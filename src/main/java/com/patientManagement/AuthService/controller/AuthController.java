package com.patientManagement.AuthService.controller;

import com.patientManagement.AuthService.DTO.LoginRequestDTO;
import com.patientManagement.AuthService.DTO.LoginResponseDTO;
import com.patientManagement.AuthService.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        Optional<String> tokenOptional = authService.authenticate(loginRequest);
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Validate token")
    @GetMapping(path = "/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
        }
        String authToken = authHeader.substring(7);
        boolean isTokenValid = authService.validateToken(authToken);
        return isTokenValid?
                ResponseEntity.ok().body("Token is valid!") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
    }
}
