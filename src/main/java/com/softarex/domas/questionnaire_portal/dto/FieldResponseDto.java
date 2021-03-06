package com.softarex.domas.questionnaire_portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class FieldResponseDto {

    @NotBlank
    @NotNull
    private String value;

    @Positive
    private Integer position;

}
