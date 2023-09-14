package com.company.services;

import com.company.config.security.JwtService;
import com.company.domains.AuthUser;
import com.company.domains.UserSMS;
import com.company.dtos.authuser.*;
import com.company.enums.Role;
import com.company.enums.Status;
import com.company.enums.TokenType;
import com.company.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final JwtService jwtService;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserSMSService userSMSService;

    public AuthUser create(@NonNull UserCreateDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Password and ConfirmPassword must be the same");
        }
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .status(Status.NO_ACTIVE)
                .build();
        authUser = authUserRepository.save(authUser);
        return authUser;
    }

    public AuthUser findByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username '%s' not found".formatted(username)));
    }

    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.username();
        String password = tokenRequest.password();
        findByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtService.generateToken(username);
    }

    public TokenResponse refreshAccessToken(@NonNull RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        if (!jwtService.isValidToken(refreshToken, TokenType.REFRESH))
            throw new CredentialsExpiredException("Token is invalid");

        String username = jwtService.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findByUsername(username);
        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtService.getExpiry(refreshToken, TokenType.REFRESH))
                .build();
        return jwtService.generateAccessToken(username, tokenResponse);
    }

    public boolean validateToken(String token) {
        return jwtService.isValidToken(token, TokenType.ACCESS) &&
                findByUsername(jwtService.getUsername(token, TokenType.ACCESS)).getStatus().equals(Status.ACTIVE);
    }

    public AuthUser activateUser(UserActivationDTO dto) {
        AuthUser authUser = findByUsername(dto.username());
        UserSMS userSMS = userSMSService.findByUserId(authUser.getId());
        if (!Objects.isNull(userSMS) && userSMS.getCode().equals(dto.code())) {
            authUser.setStatus(Status.ACTIVE);
            authUserRepository.save(authUser);
            userSMS.setExpired(true);
            userSMSService.update(userSMS);
            return authUser;
        }
        throw new RuntimeException("Code is invalid");
    }
}
