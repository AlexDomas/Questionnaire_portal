package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;
import com.softarex.domas.questionnaire_portal.constants.PasswordRegularExpression;
import com.softarex.domas.questionnaire_portal.validator.annotation.CurrentPassword;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class ChangePasswordDto {

       private String currentPassword;
       private String newPassword;

    }



