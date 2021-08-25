package com.handemdowns.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdSendEmailDto {
	@Min(1)
	private Long adId;

	@Email
	@Size(min = 5, max = 50)
	@NotEmpty
    private String email;

	@Size(min = 1, max = 50)
	@NotEmpty
    private String name;

	@Size(min = 1, max = 1000)
	@NotEmpty
    private String message;
	
	private Boolean sendCopy;
}