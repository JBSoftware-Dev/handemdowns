package com.handemdowns.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[0-9\\s\\-()+\\.]*$")
public @interface ValidPhoneNumber {
    String message() default "Invalid Phone Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}