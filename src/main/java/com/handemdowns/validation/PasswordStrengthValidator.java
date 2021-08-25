package com.handemdowns.validation;

import com.google.common.base.Joiner;
import org.passay.*;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {
    @Override
    public void initialize(PasswordStrength constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(password)) {
            return true;
        }

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
        		new LengthRule(5, 50),
        		new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on("\n").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }
}