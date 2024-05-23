package com.travelink.auth.controller;

import com.travelink.auth.dto.MemberCreateRequest;
import com.travelink.auth.dto.MemberLoginRequest;
import com.travelink.auth.dto.TokenResponse;
import com.travelink.auth.service.AuthService;
import com.travelink.auth.service.JwtAuthService;
import com.travelink.auth.support.JwtLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        TokenResponse tokenResponse = authService.login(memberLoginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        authService.register(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(final HttpServletRequest request) {
        String token = request.getHeader("refreshtoken");
        TokenResponse tokenResponse = authService.refreshToken(token);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@JwtLogin final String loginId) {
        authService.logout(loginId);
        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("/sign-up/{memberId}")
    public ResponseEntity<Boolean> checkSignId(@PathVariable("memberId") final String memberId) {
        System.out.println(memberId);
        boolean exist = authService.checkSignId(memberId);
        return ResponseEntity.ok(exist);
    }
}
