package com.company.controllers;

import com.company.domains.AuthUser;
import com.company.domains.UserSMS;
import com.company.dtos.MessageSendDTO;
import com.company.dtos.authuser.*;
import com.company.rabbitmq.producer.RabbitMQProducer;
import com.company.services.AuthUserService;
import com.company.services.UserSMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthUserService authUserService;
    private final UserSMSService userSMSService;
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/user/register")
    public ResponseEntity<AuthUser> register(@Valid @RequestBody UserCreateDTO dto) throws JsonProcessingException {
        System.err.println(dto.toString());
        AuthUser authUser = authUserService.create(dto);
        UserSMS smsCode = userSMSService.createSMSCode(authUser);
        rabbitMQProducer.sendMessage(new MessageSendDTO(authUser.getEmail(), smsCode.getCode()));
        return ResponseEntity.ok(authUser);
    }

    @PostMapping("/token/access")
    public ResponseEntity<TokenResponse> generateToken(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByUsername(tokenRequest.username()).getRole());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokenResponse = authUserService.refreshAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/token/validate")
    public Boolean validateToken(@RequestParam(name = "token") String token) {
        try {
            return authUserService.validateToken(token);
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
            return false;
        }
    }
    @PutMapping("/user/activate")
    public ResponseEntity<AuthUser> activateUser(UserActivationDTO dto) {
        AuthUser authUser = authUserService.activateUser(dto);
        return ResponseEntity.ok(authUser);
    }
}

