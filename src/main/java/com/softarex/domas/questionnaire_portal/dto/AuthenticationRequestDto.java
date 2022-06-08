package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequestDto {

    @Email(message = MessageErrorConstant.MESSAGE_INCORRECT_FORMAT_OF_EMAIL)
    @NotBlank(message = MessageErrorConstant.MESSAGE_NOT_BLANK_EMAIL)
    private String email;
    @Size(max = 40, min = 8, message = MessageErrorConstant.MESSAGE_INCORRECT_FORMAT_OF_CURRENT_PASSWORD)
    @NotBlank(message = MessageErrorConstant.MESSAGE_NOT_BLANK_CURRENT_PASSWORD)
    private String password;

}
