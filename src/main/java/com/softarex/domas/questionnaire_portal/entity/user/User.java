package com.softarex.domas.questionnaire_portal.entity.user;


import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String login;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
    @ToString.Exclude
    private Questionnaire questionnaire;

}