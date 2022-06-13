package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;

public class UniqueEmailException extends RuntimeException {

    public UniqueEmailException(String email) {
        super(MessageErrorConstant.MESSAGE_EXCEPTION_UNIQUE_EMAIL + ": " + email);
    }

}
