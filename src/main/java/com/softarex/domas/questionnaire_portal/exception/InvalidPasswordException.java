package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super(MessageErrorConstant.MESSAGE_EXCEPTION_INVALID_PASSWORD);
    }

}
