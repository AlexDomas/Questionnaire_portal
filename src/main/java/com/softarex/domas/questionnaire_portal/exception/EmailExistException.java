package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorUserInfoConstant;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String email) {
        super(MessageErrorUserInfoConstant.MESSAGE_EMAIL_EXIST_EXCEPTION + email);
    }

}
