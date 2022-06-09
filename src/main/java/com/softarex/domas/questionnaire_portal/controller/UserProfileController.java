package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.ChangePasswordDto;
import com.softarex.domas.questionnaire_portal.dto.UserProfileDataDto;
import com.softarex.domas.questionnaire_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
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
            @Valid @RequestBody ChangePasswordDto changePasswordDto,
            Principal principal) {
        return ResponseEntity.ok(userService.updatePassword(principal, changePasswordDto));
    }

    @GetMapping("/id")
    public ResponseEntity<String> getUserId(Principal principal) {
        return ResponseEntity.ok(userService.findIdByEmail(principal));
    }


}
