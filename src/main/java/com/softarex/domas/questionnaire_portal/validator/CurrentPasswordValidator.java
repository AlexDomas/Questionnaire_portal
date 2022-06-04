package com.softarex.domas.questionnaire_portal.validator;


import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.repository.UserRepository;
import com.softarex.domas.questionnaire_portal.validator.annotation.CurrentPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CurrentPasswordValidator implements ConstraintValidator<CurrentPassword, String> {
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public CurrentPasswordValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String newPassword, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
        return optionalUser.isPresent() && passwordEncoder.matches(newPassword, optionalUser.get().getPassword());
    }
}
