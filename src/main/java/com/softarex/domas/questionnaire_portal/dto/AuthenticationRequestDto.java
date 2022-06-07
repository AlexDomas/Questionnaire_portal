package com.softarex.domas.questionnaire_portal.dto;

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

    @Email(message = "Enter correct format email")
    @NotBlank(message = "Email can't be empty")
    private String email;
    @Size(max = 50, min = 7, message = "Enter a valid password")
    @NotBlank(message = "Enter a valid password")
    private String password;

}
