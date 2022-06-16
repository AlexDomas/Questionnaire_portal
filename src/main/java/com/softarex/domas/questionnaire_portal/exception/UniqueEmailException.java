package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorUserInfoConstant;

public class UniqueEmailException extends RuntimeException {

    public UniqueEmailException(String email) {
        super(MessageErrorUserInfoConstant.MESSAGE_EXCEPTION_UNIQUE_EMAIL + ": " + email);
    }

}
