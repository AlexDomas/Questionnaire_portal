package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.ChangePasswordDto;
import com.softarex.domas.questionnaire_portal.dto.UserProfileDataDto;
import com.softarex.domas.questionnaire_portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/edit_profile")
    public ResponseEntity<UserProfileDataDto> getData(Principal principal) {
        UserProfileDataDto userDataDto = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userDataDto);
    }

    @PutMapping("/edit_profile")
    public ResponseEntity<UserProfileDataDto> updateUser(@Valid @RequestBody UserProfileDataDto updateProfileDataDto,
            Principal principal) {
        return ResponseEntity.ok(userService.update(principal, updateProfileDataDto));
    }

    @PutMapping("/change_password")
    public ResponseEntity<Boolean> updatePassword(
            @Valid @RequestBody @Parameter(description = "New & old passwords") ChangePasswordDto changePasswordDto,
            Principal principal) {
        return ResponseEntity.ok(userService.updatePassword(principal, changePasswordDto));
    }

    @GetMapping("/id")
    public ResponseEntity<String> getUserId(Principal principal) {
        return ResponseEntity.ok(userService.findIdByEmail(principal));
    }


}
