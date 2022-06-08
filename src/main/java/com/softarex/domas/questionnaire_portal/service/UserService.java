package com.softarex.domas.questionnaire_portal.service;

import com.softarex.domas.questionnaire_portal.constants.MessageMailConstants;
import com.softarex.domas.questionnaire_portal.dto.ChangePasswordDto;
import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.dto.UserProfileDataDto;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.EmailExistException;
import com.softarex.domas.questionnaire_portal.exception.InvalidPasswordException;
import com.softarex.domas.questionnaire_portal.repository.UserRepository;
import com.softarex.domas.questionnaire_portal.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + username + " does not exist");
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                Collections.emptyList());
    }

    @Transactional
    public UserProfileDataDto update(Principal principal, UserProfileDataDto userProfileDto) {
        User oldUserData = userRepository.findByEmail(principal.getName()).get();
        if (userProfileDto.getEmail().equals(principal.getName()) || !isNotUserExist(userProfileDto.getEmail())) {
            updateUserData(userProfileDto, oldUserData);
            userRepository.save(oldUserData);
        }
        return userMapper.toUserDto(oldUserData);
    }

    @Transactional
    public Boolean updatePassword(Principal principal, ChangePasswordDto passwordDto) {
        User user = userRepository.findByEmail(principal.getName()).get();
        if (passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
            changePassword(passwordDto, user);
            return true;
        } else {
            throw new InvalidPasswordException();
        }
    }


    public UserProfileDataDto findByPrincipal(Principal principal) {
        return userMapper.toUserDto(userRepository.findByEmail(principal.getName()).get());
    }

    public String findIdByEmail(Principal principal) {
        return userRepository.findByEmail(principal.getName()).get().getId().toString();
    }


    public UserProfileDataDto save(UserDto userDto) {
        User user = userMapper.toUserEntity(userDto);
        if (!isNotUserExist(user.getEmail())) {
            userRepository.save(user);
        }
        mailService.sendMessage(user.getEmail(), MessageMailConstants.MESSAGE_REGISTRATION_SUBJECT, MessageMailConstants.MESSAGE_REGISTRATION_TEXT);
        return userMapper.toUserDto(user);
    }



    public UUID findIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " does not exist");
        }
        return user.get().getId();
    }


    private void updateUserData(UserProfileDataDto userDto, User oldData) {
        oldData.setEmail(userDto.getEmail());
        oldData.setFirstname(userDto.getFirstName());
        oldData.setLastname(userDto.getLastName());
        oldData.setPhone(userDto.getPhone());
    }

    private void changePassword(ChangePasswordDto passwordDto, User user) {
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        mailService.sendMessage(user.getEmail(), MessageMailConstants.MESSAGE_CHANGE_PASSWORD_SUBJECT, MessageMailConstants.MESSAGE_CHANGE_PASSWORD_TEXT);
    }

    private boolean isNotUserExist(String email) {
        if (isEmailExist(email)) {
            throw new EmailExistException(email);
        }
        return false;
    }

    private boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
