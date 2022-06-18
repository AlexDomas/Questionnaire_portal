package com.softarex.domas.questionnaire_portal.service;

import com.softarex.domas.questionnaire_portal.dto.FieldDto;
import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.field.FieldOption;
import com.softarex.domas.questionnaire_portal.entity.field.FieldResponse;
import com.softarex.domas.questionnaire_portal.entity.field.FieldType;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.FieldNotExistException;
import com.softarex.domas.questionnaire_portal.exception.QuestionnaireNotExistException;
import com.softarex.domas.questionnaire_portal.repository.*;
import com.softarex.domas.questionnaire_portal.util.FieldMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldRepository fieldRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final UserRepository userRepository;
    private final FieldOptionRepository fieldOptionRepository;
    private final FieldResponseRepository fieldResponseRepository;
    private final FieldMapper mapper;

    public Page<FieldDto> findAllByUserEmail(Principal principal, Pageable pageable) {
        Page<Field> fields = getAllField(principal, pageable);
        return fields.map(mapper::toFieldDto);
    }

    public List<FieldDto> findAllByUserId(UUID userId) {
        return getAllField(userId).stream().map(mapper::toFieldDto).collect(Collectors.toList());
    }

    public FieldDto getFieldDto(Principal principal, Integer fieldPosition) {
        return mapper.toFieldDto(getField(principal.getName(), fieldPosition - 1));
    }

    @Transactional
    public FieldDto save(Principal principal, FieldDto fieldDto) {
        Questionnaire questionnaire = getQuestionnaire(principal);
        Field field = getPreparedField(fieldDto, questionnaire);
        fieldRepository.save(field);
        saveFieldOptions(field);
        return mapper.toFieldDto(field);
    }

    @Transactional
    public FieldDto delete(Principal principal, Integer fieldPosition) {
        Field field = getField(principal.getName(), fieldPosition - 1);
        FieldDto result = getFieldDto(principal, fieldPosition);
        deleteDependEntities(field);
        List<Field> fields = calculateNewPositions(principal, fieldPosition);
        fieldRepository.delete(field);
        fieldRepository.saveAll(fields);
        return result;
    }

    @Transactional
    public FieldDto update(Principal principal, Integer fieldPosition, FieldDto fieldDto) {
        Field field = getField(principal.getName(), fieldPosition - 1);
        Field newField = updateOldFieldData(fieldDto, field);
        fieldRepository.save(newField);
        saveFieldOptions(newField);
        return mapper.toFieldDto(newField);
    }

    private Field updateOldFieldData(FieldDto fieldDto, Field field) {
        deleteDependEntities(field);
        Field newField = mapper.toFieldEntity(fieldDto);
        newField.setPosition(field.getPosition());
        newField.setId(field.getId());
        newField.setQuestionnaire(field.getQuestionnaire());
        return newField;
    }

    private Field getPreparedField(FieldDto fieldDto, Questionnaire questionnaire) {
        Field field = mapper.toFieldEntity(fieldDto);
        field.setPosition(fieldRepository.countAllByQuestionnaire(questionnaire));
        field.setQuestionnaire(questionnaire);
        return field;
    }

    private List<Field> calculateNewPositions(Principal principal, Integer fieldPosition) {
        List<Field> fields = getAllField(principal);
        fields = fields.stream()
                .filter(f -> f.getPosition() > fieldPosition - 1)
                .peek(f -> f.setPosition(f.getPosition() - 1))
                .collect(Collectors.toList());
        return fields;
    }

    private List<Field> getAllField(Principal principal) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findByUser_Email(principal.getName());
        return questionnaire.isPresent()
                ? fieldRepository.findAllByQuestionnaire_IdOrderByPositionAsc(questionnaire.get().getId())
                : Collections.emptyList();
    }

    private List<Field> getAllField(UUID userId) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findByUser_Id(userId);
        return questionnaire.isPresent()
                ? fieldRepository.findAllByQuestionnaire_IdOrderByPositionAsc(questionnaire.get().getId())
                : Collections.emptyList();
    }

    private Page<Field> getAllField(Principal principal, Pageable pageable) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findByUser_Email(principal.getName());
        return questionnaire.isPresent()
                ? fieldRepository.findAllByQuestionnaire_IdOrderByPositionAsc(questionnaire.get().getId(), pageable)
                : new PageImpl<>(Collections.emptyList());
    }

    private void deleteDependEntities(Field field) {
        deleteFieldOptions(field);
        deleteQuestionnaireResponses(field.getPosition(), field.getQuestionnaire());
    }

    private void deleteQuestionnaireResponses(Integer position, Questionnaire questionnaire) {
        List<FieldResponse> responses = fieldResponseRepository.findAllByResponse_Questionnaire(questionnaire);
        responses = responses
                .stream()
                .filter(r -> r.getPosition().equals(position))
                .collect(Collectors.toList());
        if (!responses.isEmpty()) {
            fieldResponseRepository.deleteAll(responses);
        }
    }

    private Field getField(String currentUserEmail, Integer fieldPosition) {
        List<Field> fields = getQuestionnaireFields(currentUserEmail, fieldPosition);
        return fields.stream().filter(f -> Objects.equals(f.getPosition(), fieldPosition)).findFirst().get();
    }

    private void deleteFieldOptions(Field field) {
        if (isFieldMultivariate(field)) {
            List<FieldOption> fieldOptions = fieldOptionRepository.findAllByField(field);
            if (!fieldOptions.isEmpty()) {
                fieldOptionRepository.deleteAll(fieldOptions);
            }
        }
    }

    private void saveFieldOptions(Field field) {
        if (isFieldMultivariate(field)) {
            for (FieldOption o : field.getOptions()) {
                o.setField(field);
            }
            fieldOptionRepository.saveAll(field.getOptions());
        }
    }

    private boolean isFieldMultivariate(Field field) {
        return field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON;
    }

    private List<Field> getQuestionnaireFields(String currentUserEmail, Integer fieldPosition) {
        Questionnaire questionnaire = questionnaireRepository.findByUser_Email(currentUserEmail)
                .orElseThrow(() -> new QuestionnaireNotExistException("There are not questionnaire & fields for the current user"));
        List<Field> fields = fieldRepository.findAllByQuestionnaire_IdOrderByPositionAsc(questionnaire.getId());
        if (fields.size() <= fieldPosition) {
            throw new FieldNotExistException(fieldPosition + 1);
        }
        return fields;
    }

    private Questionnaire getQuestionnaire(Principal principal) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findByUser_Email(principal.getName());
        return questionnaire.orElseGet(() -> createQuestionnaire(principal));
    }

    private Questionnaire createQuestionnaire(Principal principal) {
        Questionnaire newQuestionnaire = new Questionnaire();
        User user = userRepository.findByEmail(principal.getName()).get();
        user.setQuestionnaire(newQuestionnaire);
        newQuestionnaire.setUser(user);
        questionnaireRepository.save(newQuestionnaire);
        userRepository.save(user);
        return newQuestionnaire;
    }

}