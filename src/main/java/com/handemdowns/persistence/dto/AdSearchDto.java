package com.handemdowns.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdSearchDto {
    @NotNull
    private String topic;

    @NotEmpty
    private String categoryCode;

    @NotEmpty
    private String locationCode;

    public Boolean willDeliver;
}