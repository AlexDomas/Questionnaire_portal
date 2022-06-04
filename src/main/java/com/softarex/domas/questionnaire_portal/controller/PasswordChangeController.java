package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.ChangePasswordDto;
import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.UserNotFoundException;
import com.softarex.domas.questionnaire_portal.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class PasswordChangeController {

    private final static Logger logger = LogManager.getLogger();
    private UserService userService;

    @Autowired
    public PasswordChangeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change_password")
    public String changePassword(Model model, Principal principal) throws UserNotFoundException {
        User user;
        ChangePasswordDto dto = new ChangePasswordDto();
        user = userService.findByEmail(principal.getName());
        model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("changePasswordDto", dto);
        return "change_password";
    }

    @PostMapping("/change_password")
    public String changePasswordAction(@Valid ChangePasswordDto changePasswordDto,
                                       BindingResult bindingResult,
                                       Authentication authentication, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                User user = userService.findByEmail(authentication.getName());
                model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
                userService.updateUserPassword(user, changePasswordDto.getNewPassword());
                return "redirect:change_password?success";
            } catch (UserNotFoundException exception) {
                logger.log(Level.ERROR, exception.getMessage());
            }
        }
        return "change_password";
    }



}
