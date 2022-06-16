package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorFieldConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class FieldDto {

    @NotBlank(message = MessageErrorFieldConstants.MESSAGE_ERROR_LABEL_NOT_BLANK)
    private String label;

    private boolean active;

    private boolean required;

    @NotBlank(message = MessageErrorFieldConstants.MESSAGE_ERROR_FIELD_TYPE)
    @Pattern(regexp = "[A-Z_]{4,16}", message = MessageErrorFieldConstants.MESSAGE_ERROR_FIELD_TYPE)
    private String fieldType;

    private String fieldOptions;

}