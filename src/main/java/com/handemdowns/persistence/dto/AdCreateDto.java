package com.handemdowns.persistence.dto;

import com.handemdowns.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdCreateDto {
	@Size(min = 1, max = 50)
	@NotEmpty
    private String title;

	@Size(min = 1, max = 1000)
	@NotEmpty
    private String description;

	@ValidPhoneNumber
	@NotNull
	private String phoneNumber;

	@NotEmpty
	private String categoryCode;

    private String locationCode;

	@NotEmpty
	private String postalCode;

    private Boolean willDeliver;

	@Basic
	@Email
	private String email;
}