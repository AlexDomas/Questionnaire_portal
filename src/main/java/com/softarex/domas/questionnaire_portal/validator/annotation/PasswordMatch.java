package com.softarex.domas.questionnaire_portal.validator.annotation;


import com.softarex.domas.questionnaire_portal.validator.PasswordMatchValidator;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordMatchValidator.class})
public @interface PasswordMatch {
    String message() default "Passwords didn't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
