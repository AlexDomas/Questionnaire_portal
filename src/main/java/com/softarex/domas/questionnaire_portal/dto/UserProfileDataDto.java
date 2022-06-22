package com.softarex.domas.questionnaire_portal.dto;


import com.softarex.domas.questionnaire_portal.constants.MessageErrorUserInfoConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class UserProfileDataDto {

    @NotBlank(message = MessageErrorUserInfoConstant.MESSAGE_ERROR_FIRST_NAME)
    @Pattern(regexp = "[a-zA-Z]{1,40}", message = MessageErrorUserInfoConstant.MESSAGE_ERROR_MAX_MIN_LENGTH_OF_FIRST_NAME)
    private String firstname;

    @NotBlank(message = MessageErrorUserInfoConstant.MESSAGE_ERROR_LAST_NAME)
    @Pattern(regexp = "[a-zA-Z]{1,40}", message = MessageErrorUserInfoConstant.MESSAGE_ERROR_MAX_MIN_LENGTH_OF_LAST_NAME)
    private String lastname;

    @Email(message = MessageErrorUserInfoConstant.MESSAGE_INCORRECT_FORMAT_OF_EMAIL)
    @NotBlank(message = MessageErrorUserInfoConstant.MESSAGE_NOT_BLANK_EMAIL)
    private String email;

    @NotBlank
    @Pattern(regexp = "[+]?[\\d]{11,14}", message = MessageErrorUserInfoConstant.MESSAGE_INCORRECT_FORMAT_OF_PHONE_NUMBER)
    private String phone;

}
