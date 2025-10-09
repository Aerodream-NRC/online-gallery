package com.aerodream.user_service.Service;

import com.aerodream.user_service.Config.JwtTokenProvider;
import com.aerodream.user_service.Dto.User.JwtResponse;
import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserLoginDto;
import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.aerodream.user_service.Enum.RoleEnum.ROLE_USER;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtResponse authenticateUser(UserLoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        log.info("User {} logged in successfully", user.getLogin());

        return new JwtResponse(jwt, refreshToken, user);
    }

    public JwtResponse registerUser(UserCreateDto registerRequest) {
        if (userRepository.existsByLogin(registerRequest.getLogin())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        UserEntity user = new UserEntity();
        user.setLogin(registerRequest.getLogin());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singleton(ROLE_USER));
        user.setIsEnabled(true);

        UserEntity savedUser = userRepository.save(user);

        String jwt = tokenProvider.generateToken(savedUser);
        String refreshToken = tokenProvider.generateRefreshToken(savedUser);

        log.info("User {} registered successfully", savedUser.getLogin());

        return new JwtResponse(jwt, refreshToken, savedUser);
    }

    public JwtResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newJwt = tokenProvider.generateToken(user);
        String newRefreshToken = tokenProvider.generateRefreshToken(user);

        return new JwtResponse(newJwt, newRefreshToken, user);
    }
}