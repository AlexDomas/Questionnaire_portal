package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.UserNotFoundException;
import com.softarex.domas.questionnaire_portal.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
public class FieldsController {

        private final static Logger logger = LogManager.getLogger();

        private UserService userService;

        @Autowired
        public FieldsController( UserService userService) {

            this.userService = userService;
        }

        @GetMapping("/fields")
        public String getFieldsPage(Model model, Principal principal) throws UserNotFoundException {
            User user;
            user = userService.findByEmail(principal.getName());
            model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
            return "fields";
        }


}
