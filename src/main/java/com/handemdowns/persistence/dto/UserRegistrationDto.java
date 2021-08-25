package com.handemdowns.persistence.dto;

import com.handemdowns.validation.PasswordMatches;
import com.handemdowns.validation.PasswordStrength;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@PasswordMatches
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"password", "matchingPassword"})
public class UserRegistrationDto {
	@Email
	@Size(min = 5, max = 50)
    @NotEmpty
    private String email;

	@Size(min = 1, max = 50)
	@NotEmpty
    private String name;
	
    @PasswordStrength
	@Size(min = 5, max = 50)
    @NotEmpty
    private String password;

	@Size(min = 5, max = 50)
    @NotEmpty
    private String matchingPassword;
    
    private Integer role;
}