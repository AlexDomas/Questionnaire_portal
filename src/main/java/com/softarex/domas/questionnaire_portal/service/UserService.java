package com.softarex.domas.questionnaire_portal.service;


import com.softarex.domas.questionnaire_portal.dto.UserDto;
import com.softarex.domas.questionnaire_portal.entity.user.Role;
import com.softarex.domas.questionnaire_portal.entity.user.SecurityUserDetails;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.exception.UserNotFoundException;
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


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

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




}
