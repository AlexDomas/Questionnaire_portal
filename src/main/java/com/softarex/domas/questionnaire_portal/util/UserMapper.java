package com.softarex.domas.questionnaire_portal.util;

import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.dto.UserProfileDataDto;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserProfileDataDto toUserDto(User user) {
        UserProfileDataDto userDataProfileDto = new UserProfileDataDto();
        userDataProfileDto.setEmail(user.getEmail());
        userDataProfileDto.setFirstName(user.getFirstname());
        userDataProfileDto.setLastName(user.getLastname());
        userDataProfileDto.setPhone(user.getPhone());
        return userDataProfileDto;
    }

    public User toUserEntity(UserDto userDto) {
        User user = createUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    private User createUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        return user;
    }
}
