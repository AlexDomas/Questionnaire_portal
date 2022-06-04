package com.softarex.domas.questionnaire_portal.validator.annotation;


import com.softarex.domas.questionnaire_portal.validator.CurrentPasswordValidator;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CurrentPasswordValidator.class})
public @interface CurrentPassword {
    String message() default "Wrong password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
