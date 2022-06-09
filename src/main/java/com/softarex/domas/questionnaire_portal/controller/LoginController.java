package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.AuthenticationRequestDto;
import com.softarex.domas.questionnaire_portal.dto.AuthenticationTokenDto;
import com.softarex.domas.questionnaire_portal.service.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final JwtAuthenticationService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDto> login(@Valid @RequestBody AuthenticationRequestDto authDto) {
        AuthenticationTokenDto result = jwtService.authenticate(authDto);
        return ResponseEntity.ok(result);
    }

}
