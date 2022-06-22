package com.softarex.domas.questionnaire_portal.util;

import com.softarex.domas.questionnaire_portal.dto.QuestionnaireResponseDto;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.QuestionnaireResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionnaireResponseMapper {
    private final FieldResponseMapper fieldResponseMapper;

    public QuestionnaireResponseDto toResponse(QuestionnaireResponse response) {
        QuestionnaireResponseDto responseDto = new QuestionnaireResponseDto();
        responseDto.setId(response.getId().toString());
        responseDto.setResponses(
                response.getConcreteResponses()
                        .stream()
                        .map(fieldResponseMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
        return responseDto;
    }

}