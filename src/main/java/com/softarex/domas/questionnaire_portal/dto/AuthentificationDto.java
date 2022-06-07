package com.softarex.domas.questionnaire_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthentificationDto {

    private String login;
    private String password;

}
