package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorUserInfoConstant;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super(MessageErrorUserInfoConstant.MESSAGE_EXCEPTION_INVALID_PASSWORD);
    }

}
