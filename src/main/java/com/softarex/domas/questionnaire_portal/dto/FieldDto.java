package com.softarex.domas.questionnaire_portal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDto {
    private String label;
    private boolean isActive;
    private boolean required;
    private String fieldType;
    private String fieldOptions;
}
