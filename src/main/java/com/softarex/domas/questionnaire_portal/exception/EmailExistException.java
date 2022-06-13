package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String email) {
        super(MessageErrorConstant.MESSAGE_EMAIL_EXIST_EXCEPTION + email);
    }

}
