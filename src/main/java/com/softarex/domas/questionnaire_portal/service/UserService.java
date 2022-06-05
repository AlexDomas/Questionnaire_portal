package com.softarex.domas.questionnaire_portal.service;


import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.entity.user.Role;
import com.softarex.domas.questionnaire_portal.entity.user.SecurityUserDetails;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.UserNotFoundException;
import com.softarex.domas.questionnaire_portal.property.MailProperty;
import com.softarex.domas.questionnaire_portal.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final static Logger logger = LogManager.getLogger();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final MailProperty mailProperty;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, MailProperty mailProperty) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.mailProperty = mailProperty;
    }

    @Transactional
    public void updateUser(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
    }

    @Transactional
    public void addUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRoles(Collections.singleton(new Role(1,"ROLE_USER")));
        userRepository.save(user);
        mailService.sendMessage(user, mailProperty.getRegistrationSubject(), mailProperty.getRegistrationText());

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = optionalUser.get();
        Hibernate.initialize(user.getRoles());
        return new SecurityUserDetails(user);
    }

    public UserDto findDtoByEmail(String email) throws UserNotFoundException{
        return new UserDto(userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new));
    }

    public UserDto createDto(User user) {
        return new UserDto(user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getPhoneNumber(), null, null);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        mailService.sendMessage(user, mailProperty.getPasswordChangeSubject(), mailProperty.getPasswordChangeText());
    }






}
