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
    public UserProfileDataDto getData(Principal principal) {
        UserProfileDataDto userDataDto = userService.findByPrincipal(principal);
        return userDataDto;
    }

    @PutMapping("/edit_profile")
    public UserProfileDataDto updateUser(@Valid @RequestBody UserProfileDataDto updateProfileDataDto,
            Principal principal) {
        return userService.update(principal, updateProfileDataDto);
    }

    @PutMapping("/change_password")
    public Boolean updatePassword(
            @Valid @RequestBody ChangePasswordDto changePasswordDto,
            Principal principal) {
        return userService.updatePassword(principal, changePasswordDto);
    }

    @GetMapping("/id")
    public String getUserId(Principal principal) {
        return userService.findIdByEmail(principal);
    }


}
