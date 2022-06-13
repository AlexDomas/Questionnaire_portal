package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.AuthenticationRequestDto;
import com.softarex.domas.questionnaire_portal.dto.AuthenticationTokenDto;
import com.softarex.domas.questionnaire_portal.service.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final JwtAuthenticationService jwtService;

    @PostMapping("/login")
    public AuthenticationTokenDto login(@Valid @RequestBody AuthenticationRequestDto authDto) {
        return jwtService.authenticate(authDto);
    }

}
