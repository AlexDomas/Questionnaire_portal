package com.softarex.domas.questionnaire_portal.service;

import com.softarex.domas.questionnaire_portal.dto.FieldResponseDto;
import com.softarex.domas.questionnaire_portal.dto.QuestionnaireResponseDto;
import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.field.FieldOption;
import com.softarex.domas.questionnaire_portal.entity.field.FieldResponse;
import com.softarex.domas.questionnaire_portal.entity.field.FieldType;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.QuestionnaireResponse;
import com.softarex.domas.questionnaire_portal.exception.FieldNotExistException;
import com.softarex.domas.questionnaire_portal.exception.QuestionnaireNotExistException;
import com.softarex.domas.questionnaire_portal.exception.QuestionnaireResponseException;
import com.softarex.domas.questionnaire_portal.repository.*;
import com.softarex.domas.questionnaire_portal.util.FieldResponseMapper;
import com.softarex.domas.questionnaire_portal.util.QuestionnaireResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldResponseService {

    private static final String NO_DATA_STRING = "N/A";
    private static final String RESPONSE_OPTIONS_DELIMITER = ", ";
    private final FieldRepository fieldRepository;
    private final FieldResponseRepository fieldResponseRepository;
    private final FieldOptionRepository fieldOptionsRepository;
    private final FieldResponseMapper fieldResponseMapper;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionnaireResponseMapper questionnaireResponseMapper;
    private final QuestionnaireResponseRepository questionnaireResponseRepository;

    public Page<QuestionnaireResponseDto> findAllByUserId(Principal principal, Pageable pageable) {
        Questionnaire questionnaire = questionnaireRepository.findByUserEmail(principal.getName())
                .orElseThrow(() -> new QuestionnaireNotExistException("Such questionnaire is not exist"));
        Page<QuestionnaireResponse> questionnaireResponses = questionnaireResponseRepository
                .findAllByQuestionnaireOrderByCreationDate(questionnaire, pageable);
        return questionnaireResponses.map(questionnaireResponseMapper::toResponse);
    }

    @Transactional
    public List<FieldResponseDto> saveAll(List<FieldResponseDto> responses, UUID userId) {
        Questionnaire questionnaire = getQuestionnaire(userId);
        List<Field> questionnaireFields = fieldRepository.findAllByQuestionnaireIdOrderByPositionAsc(questionnaire.getId());
        validateFieldResponseDtos(responses, questionnaireFields);
        List<FieldResponse> fieldResponses = saveFieldResponses(responses, questionnaire, questionnaireFields);
        return fieldResponses.stream().map(fieldResponseMapper::toResponseDto).collect(Collectors.toList());
    }

    private List<FieldResponse> saveFieldResponses(List<FieldResponseDto> responses, Questionnaire questionnaire, List<Field> questionnaireFields) {
        QuestionnaireResponse response = prepareQuestionnaireResponse(questionnaire);
        List<FieldResponse> fieldResponses = getFieldResponses(responses, questionnaireFields, response);
        fieldResponseRepository.saveAll(fieldResponses);
        response.setConcreteResponses(fieldResponses);
        return fieldResponses;
    }

    private void validateFieldResponseDtos(List<FieldResponseDto> responses, List<Field> questionnaireFields) {
        checkCopiedAnswers(responses);
        checkRequiredAnswers(responses, getRequiredFieldsPositions(getRequiredFields(questionnaireFields)));
    }

    private QuestionnaireResponse prepareQuestionnaireResponse(Questionnaire questionnaire) {
        QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
        questionnaireResponse.setCreationDate(new Date());
        questionnaireResponse.setQuestionnaire(questionnaire);
        questionnaireResponseRepository.save(questionnaireResponse);
        return questionnaireResponse;
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
        return (field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON || field.getFieldType() == FieldType.CHECKBOX)
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
                throw new QuestionnaireResponseException("Required value is empty");
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
                throw new QuestionnaireResponseException("No such response value: " + response.getValue());
            }
        }
    }

    private void checkComboboxResponseValues(Field field, FieldResponse response, List<FieldOption> options) {
        if (field.getFieldType() == FieldType.COMBOBOX) {
            List<String> fieldResponses = Arrays.stream(response.getValue().split(RESPONSE_OPTIONS_DELIMITER)).toList();
            if (!options.stream().map(FieldOption::getValue).collect(Collectors.toList()).containsAll(fieldResponses)) {
                throw new QuestionnaireResponseException("Invalid combobox response values: " + fieldResponses);
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
            throw new QuestionnaireResponseException("There are not all required responses in the answer");
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
            throw new QuestionnaireResponseException("There are 2 or more responses for the 1 field");
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

    private Questionnaire getQuestionnaire(UUID userId) {
        return questionnaireRepository.findByUserId(userId)
                .orElseThrow(() -> new QuestionnaireNotExistException("Questionnaire does not exist, id: " + userId.toString()));
    }

}
