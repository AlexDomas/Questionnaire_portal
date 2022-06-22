package com.softarex.domas.questionnaire_portal.exception;

public class FieldNotExistException extends RuntimeException{

    public FieldNotExistException(Integer position) {
        super("Field does not exist: " + position);
    }

}
