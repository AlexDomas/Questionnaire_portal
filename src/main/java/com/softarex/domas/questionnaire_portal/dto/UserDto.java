package com.softarex.domas.questionnaire_portal.dto;


import com.softarex.domas.questionnaire_portal.entity.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;


public class UserDto {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String password;
    @JsonIgnore
    private String repeatedPassword;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }

    public UserDto(String email, String firstName, String lastName, String phoneNumber, String password, String repeatedPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(phoneNumber, userDto.phoneNumber) && Objects.equals(password, userDto.password) && Objects.equals(repeatedPassword, userDto.repeatedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, phoneNumber, password, repeatedPassword);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("Email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(lastName).append('\'');
        sb.append(", mobilePhone='").append(phoneNumber).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", repeatedPassword='").append(repeatedPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
