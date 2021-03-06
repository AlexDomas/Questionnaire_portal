package com.softarex.domas.questionnaire_portal.util;

import com.softarex.domas.questionnaire_portal.dto.FieldDto;
import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.field.FieldOption;
import com.softarex.domas.questionnaire_portal.entity.field.FieldType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class FieldMapper {
    private final static String FIELD_OPTIONS_DELIMITER = "~!@#%&_&%#@!~";

    public FieldDto toFieldDto(Field field) {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setFieldType(field.getFieldType().toString());
        fieldDto.setActive(field.isActive());
        fieldDto.setLabel(field.getLabel());
        fieldDto.setRequired(field.isRequired());
        if (field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON || field.getFieldType() == FieldType.CHECKBOX) {
            fieldDto.setFieldOptions(field.getOptions()
                    .stream()
                    .map(FieldOption::getValue)
                    .collect(Collectors.joining(FIELD_OPTIONS_DELIMITER)));
        }
        return fieldDto;
    }

    public Field toFieldEntity(FieldDto fieldDto) {
        Field field = new Field();
        field.setActive(fieldDto.isActive());
        field.setRequired(fieldDto.isRequired());
        field.setLabel(fieldDto.getLabel());
        field.setFieldType(FieldType.valueOf(fieldDto.getFieldType()));
        if (field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON || field.getFieldType() == FieldType.CHECKBOX) {
            field.setOptions(Arrays.stream(fieldDto.getFieldOptions()
                            .split(FIELD_OPTIONS_DELIMITER))
                    .map(FieldOption::new)
                    .collect(Collectors.toSet()));
        }
        return field;
    }

}