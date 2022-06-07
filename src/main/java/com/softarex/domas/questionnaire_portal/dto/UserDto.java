package com.softarex.domas.questionnaire_portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto extends UserProfileDataDto{
    @NotBlank(message = "Enter a valid field password")
    @Size(max = 40, min = 10, message = "Enter a valid field password")
    private String password;

}
