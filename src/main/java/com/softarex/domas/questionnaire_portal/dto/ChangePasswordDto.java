package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ChangePasswordDto {

        @Size(max = 40, min = 8, message = MessageErrorConstant.MESSAGE_INCORRECT_FORMAT_OF_CURRENT_PASSWORD)
        @NotBlank(message = MessageErrorConstant.MESSAGE_NOT_BLANK_CURRENT_PASSWORD)
        private String currentPassword;

        @Size(max = 40, min = 8, message = MessageErrorConstant.MESSAGE_INCORRECT_FORMAT_OF_NEW_PASSWORD)
        @NotBlank(message = MessageErrorConstant.MESSAGE_NOT_BLANK_NEW_PASSWORD)
        private String newPassword;

    }



