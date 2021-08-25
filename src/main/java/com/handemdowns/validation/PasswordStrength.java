package com.handemdowns.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {
    String message() default "Password not strong enough";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}