package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
            return "fields";
        }


}
