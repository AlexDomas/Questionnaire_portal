package com.softarex.domas.questionnaire_portal.validator.annotation;

import com.softarex.domas.questionnaire_portal.validator.UniqueEmailValidator;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueEmailValidator.class})
public @interface UniqueEmail {
    String message() default "User with such email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
