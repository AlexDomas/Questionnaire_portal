package com.softarex.domas.questionnaire_portal.entity.user;


import com.softarex.domas.questionnaire_portal.entity.BaseEntity;

import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstname;
    private String lastname;
    private String email;
    private String login;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "user")
    private Questionnaire questionnaire;

}