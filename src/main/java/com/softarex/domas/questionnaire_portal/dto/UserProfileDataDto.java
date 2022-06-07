package com.softarex.domas.questionnaire_portal.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class UserProfileDataDto {

    @NotBlank(message = "Enter a valid field first name")
    @Pattern(regexp = "[a-zA-Z]{1,40}", message = "Enter a valid field first name")
    private String firstName;

    @NotBlank(message = "Enter a valid field last name")
    @Pattern(regexp = "[a-zA-Z]{1,40}", message = "Enter a valid field last name")
    private String lastName;

    @Email(message = "Enter a valid field email address")
    @NotBlank(message = "Enter a valid field email address")
    private String email;

    @NotBlank
    @Pattern(regexp = "[+]?[\\d]{11,14}", message = "Enter a valid field phone number")
    private String phone;

}
