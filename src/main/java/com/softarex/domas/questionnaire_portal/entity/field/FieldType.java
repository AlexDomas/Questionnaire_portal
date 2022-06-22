package com.softarex.domas.questionnaire_portal.entity.field;

public enum FieldType {

    SINGLE_LINE_TEXT,
    MULTILINE_TEXT,
    RADIO_BUTTON,
    CHECKBOX,
    COMBOBOX,
    DATE;

    public static boolean isFieldMultivariate(Field field) {
        return field.getFieldType() == FieldType.COMBOBOX || field.getFieldType() == FieldType.RADIO_BUTTON;
    }

}
