package com.softarex.domas.questionnaire_portal.dto;

import com.softarex.domas.questionnaire_portal.validator.annotation.CurrentPassword;

import java.util.Objects;

public class ChangePasswordDto {

        @CurrentPassword
        private String currentPassword;

        private String newPassword;

        private String confirmNewPassword;

        public ChangePasswordDto() {
        }

        public ChangePasswordDto(String currentPassword, String newPassword, String confirmNewPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
            this.confirmNewPassword = confirmNewPassword;
        }

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmNewPassword() {
            return confirmNewPassword;
        }

        public void setConfirmNewPassword(String confirmNewPassword) {
            this.confirmNewPassword = confirmNewPassword;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChangePasswordDto dto = (ChangePasswordDto) o;
            return Objects.equals(currentPassword, dto.currentPassword) && Objects.equals(newPassword, dto.newPassword) && Objects.equals(confirmNewPassword, dto.confirmNewPassword);
        }

        @Override
        public int hashCode() {
            return Objects.hash(currentPassword, newPassword, confirmNewPassword);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ChangePasswordDto{");
            sb.append("currentPassword='").append(currentPassword).append('\'');
            sb.append(", newPassword='").append(newPassword).append('\'');
            sb.append(", confirmNewPassword='").append(confirmNewPassword).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }



