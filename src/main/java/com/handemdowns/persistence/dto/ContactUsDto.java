package com.handemdowns.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContactUsDto {
    @Size(min = 1, max = 50)
    @NotEmpty
    private String name;

    @Email
    @Size(min = 5, max = 50)
    @NotEmpty
    private String email;

    @Size(min = 1, max = 5000)
    @NotEmpty
    private String message;
}