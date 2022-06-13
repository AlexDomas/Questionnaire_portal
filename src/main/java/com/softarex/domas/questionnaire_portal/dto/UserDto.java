package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.constants.MessageErrorConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto extends UserProfileDataDto {

    @NotBlank(message = MessageErrorConstant.MESSAGE_ERROR_INVALID_PASSWORD)
    @Size(max = 40, min = 10, message = MessageErrorConstant.MESSAGE_ERROR_INVALID_PASSWORD)
    private String password;

}
