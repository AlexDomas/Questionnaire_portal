package com.softarex.domas.questionnaire_portal.exception;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;

public class EmailExistException extends RuntimeException{

    public EmailExistException(String login){
        super(MessageErrorConstant.MESSAGE_LOGIN_EXIST_EXCEPTION + login);
    }

}
