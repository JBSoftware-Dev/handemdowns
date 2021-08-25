package com.handemdowns.persistence.dto;

import com.handemdowns.validation.PasswordMatches;
import com.handemdowns.validation.PasswordStrength;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@PasswordMatches
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"oldPassword", "password", "matchingPassword"})
public class AccountSettingsDto {
	@Size(min = 5, max = 30)
	@NotEmpty
    private String oldPassword;

    @PasswordStrength
	@Size(min = 5, max = 30)
	@NotEmpty
    private String password;

	@Size(min = 5, max = 30)
	@NotEmpty
    private String matchingPassword;
}