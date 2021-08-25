package com.handemdowns.validation;

import com.handemdowns.persistence.dto.AccountSettingsDto;
import com.handemdowns.persistence.dto.UserRegistrationDto;
import com.handemdowns.persistence.dto.UserSavePasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		if (obj instanceof UserRegistrationDto) {
			UserRegistrationDto user = (UserRegistrationDto) obj;
			return user.getPassword().equals(user.getMatchingPassword());
		} else if (obj instanceof UserSavePasswordDto) {
			UserSavePasswordDto user = (UserSavePasswordDto) obj;
			return user.getPassword().equals(user.getMatchingPassword());
		} else if (obj instanceof AccountSettingsDto) {
			AccountSettingsDto user = (AccountSettingsDto) obj;
			return user.getPassword().equals(user.getMatchingPassword());
		}  else {
			return false;
		}
	}
}