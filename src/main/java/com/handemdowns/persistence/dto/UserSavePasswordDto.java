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
@ToString(exclude={"password", "matchingPassword"})
public class UserSavePasswordDto {
    @PasswordStrength
	@Size(min = 5, max = 50)
    private String password;
    
    @NotEmpty
	@Size(min = 5, max = 50)
    private String matchingPassword;
}