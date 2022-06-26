package com.softarex.domas.questionnaire_portal.service;

import com.softarex.domas.questionnaire_portal.dto.FieldResponseDto;
import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.field.FieldOption;
import com.softarex.domas.questionnaire_portal.entity.field.FieldResponse;
import com.softarex.domas.questionnaire_portal.entity.field.FieldType;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.QuestionnaireResponse;
import com.softarex.domas.questionnaire_portal.exception.FieldNotExistException;
import com.softarex.domas.questionnaire_portal.exception.QuestionnaireNotExistException;
import com.softarex.domas.questionnaire_portal.exception.FieldResponseException;
import com.softarex.domas.questionnaire_portal.repository.FieldOptionRepository;
import com.softarex.domas.questionnaire_portal.repository.QuestionnaireRepository;
import com.softarex.domas.questionnaire_portal.util.FieldResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldResponseService {

    private static final String NO_DATA_STRING = "N/A";
    private final static String RESPONSE_OPTIONS_DELIMITER = ", ";
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldOptionRepository fieldOptionsRepository;
    private final FieldResponseMapper fieldResponseMapper;

    private void validateFieldResponseDtos(List<FieldResponseDto> responses, List<Field> questionnaireFields) {
        checkCopiedAnswers(responses);
        checkRequiredAnswers(responses, getRequiredFieldsPositions(getRequiredFields(questionnaireFields)));
    }

    private List<FieldResponse> getFieldResponses(
            List<FieldResponseDto> responses,
            List<Field> questionnaireFields,
            QuestionnaireResponse questionnaireResponse) {
        List<FieldResponse> preparedFieldResponses = new ArrayList<>();
        for (FieldResponseDto responseDto : responses) {
            FieldResponse response = prepareFieldResponse(questionnaireFields, responseDto);
            response.setResponse(questionnaireResponse);
            preparedFieldResponses.add(response);
        }
        return preparedFieldResponses;
    }

    private FieldResponse prepareFieldResponse(List<Field> questionnaireFields, FieldResponseDto responseDto) {
        Field field = getField(questionnaireFields, responseDto);
        return createFieldResponse(responseDto, field);
    }

    private List<FieldOption> getFieldOptions(Field field) {
        return (field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON)
                ? fieldOptionsRepository.findAllByField(field)
                : Collections.emptyList();
    }

    private FieldResponse createFieldResponse(FieldResponseDto responseDto, Field field) {
        FieldResponse response = fieldResponseMapper.toResponseEntity(responseDto);
        setCorrespondValue(field, response);
        return response;
    }

    private void setCorrespondValue(Field field, FieldResponse response) {
        if (field.isActive()) {
            checkActiveField(field, response);
        } else {
            checkInactiveField(field, response);
        }
    }

    private void checkInactiveField(Field field, FieldResponse response) {
        if (field.isRequired()) {
            response.setValue(NO_DATA_STRING);
        } else {
            response.setValue(null);
        }
    }

    private void checkActiveField(Field field, FieldResponse response) {
        if (field.isRequired()) {
            if (response.getValue() == null || response.getValue().equals(NO_DATA_STRING)) {
                throw new FieldResponseException("Required value is empty");
            }
        }
        if (response.getValue().equals(NO_DATA_STRING)) {
            return;
        }
        checkResponseValue(field, response);
    }

    private void checkResponseValue(Field field, FieldResponse response) {
        List<FieldOption> options = getFieldOptions(field);
        checkRadiobuttonResponseValues(field, response, options);
        checkComboboxResponseValues(field, response, options);
    }

    private void checkRadiobuttonResponseValues(Field field, FieldResponse response, List<FieldOption> options) {
        if (field.getFieldType() == FieldType.RADIO_BUTTON) {
            if (options.stream().filter(o -> o.getValue().equals(response.getValue())).findFirst().isEmpty()) {
                throw new FieldResponseException("No such response value: " + response.getValue());
            }
        }
    }

    private void checkComboboxResponseValues(Field field, FieldResponse response, List<FieldOption> options) {
        if (field.getFieldType() == FieldType.COMBOBOX) {
            List<String> fieldResponses = Arrays.stream(response.getValue().split(RESPONSE_OPTIONS_DELIMITER)).toList();
            if (!options.stream().map(FieldOption::getValue).collect(Collectors.toList()).containsAll(fieldResponses)) {
                throw new FieldResponseException("Invalid combobox response values: " + fieldResponses);
            }
        }
    }

    private Field getField(List<Field> questionnaireFields, FieldResponseDto responseDto) {
        if (responseDto.getPosition() - 1 >= questionnaireFields.size()) {
            throw new FieldNotExistException(responseDto.getPosition());
        }
        return questionnaireFields.get(responseDto.getPosition() - 1);
    }

    private void checkRequiredAnswers(List<FieldResponseDto> responses, List<Integer> requiredFieldsPositions) {
        if (!isAllRequiredResponses(responses, requiredFieldsPositions)) {
            throw new FieldResponseException("There are not all required responses in the answer");
        }
    }

    private List<Integer> getRequiredFieldsPositions(List<Field> requiredFields) {
        return requiredFields
                .stream()
                .map(Field::getPosition)
                .collect(Collectors.toList());
    }

    private void checkCopiedAnswers(List<FieldResponseDto> responses) {
        if (responses.size() != getResponsesDistinctCount(responses)) {
            throw new FieldResponseException("There are 2 or more responses for the 1 field");
        }
    }

    private boolean isAllRequiredResponses(List<FieldResponseDto> responses, List<Integer> positions) {
        return responses
                .stream()
                .map(FieldResponseDto::getPosition)
                .map(p -> p - 1)
                .collect(Collectors.toList())
                .containsAll(positions);
    }

    private long getResponsesDistinctCount(List<FieldResponseDto> responses) {
        return responses
                .stream()
                .map(FieldResponseDto::getPosition)
                .distinct()
                .count();
    }

    private List<Field> getRequiredFields(List<Field> questionnaireFields) {
        return questionnaireFields
                .stream()
                .filter(f -> f.isActive() && f.isRequired())
                .collect(Collectors.toList());
    }

    private Questionnaire isQuestionnaireExist(UUID userId) {
        Questionnaire questionnaire = questionnaireRepository.findByUserId(userId)
                .orElseThrow(() -> new QuestionnaireNotExistException("Questionnaire does not exist, id: " + userId.toString()));
        return questionnaire;
    }

}
