package com.aerodream.user_service.Controller;

import com.aerodream.user_service.Dto.User.JwtResponse;
import com.aerodream.user_service.Dto.User.RefreshTokenRequest;
import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserLoginDto;
import com.aerodream.user_service.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginDto loginRequest) {
        JwtResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody UserCreateDto registerRequest) {
        JwtResponse response = authService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshRequest) {
        JwtResponse response = authService.refreshToken(refreshRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        return ResponseEntity.ok().build();
    }
}