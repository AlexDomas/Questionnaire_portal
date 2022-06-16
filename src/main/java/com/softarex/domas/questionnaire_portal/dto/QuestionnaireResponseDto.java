package com.softarex.domas.questionnaire_portal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireResponseDto {

    private String id;

    private List<FieldResponseDto> responses;

}