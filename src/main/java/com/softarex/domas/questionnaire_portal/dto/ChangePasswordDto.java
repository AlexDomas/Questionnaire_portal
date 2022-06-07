package com.softarex.domas.questionnaire_portal.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordDto {

        @Size(max = 50, min = 10, message = "Enter a valid current password")
        @NotBlank(message = "Current password is empty")
        private String currentPassword;
        @Size(max = 50, min = 10, message = "Enter a valid new password")
        @NotBlank(message = "New password is empty")
        private String newPassword;

    }



