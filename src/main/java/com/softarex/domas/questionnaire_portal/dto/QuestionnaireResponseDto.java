package com.softarex.domas.questionnaire_portal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionnaireResponseDto {

    private String id;

    private List<FieldResponseDto> responses;

}