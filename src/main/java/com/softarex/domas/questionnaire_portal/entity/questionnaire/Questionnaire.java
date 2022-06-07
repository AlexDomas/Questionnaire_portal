package com.softarex.domas.questionnaire_portal.entity.questionnaire;

import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.response.Response;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne(mappedBy = "questionnaire")
    @ToString.Exclude
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "questionnaire")
    @ToString.Exclude
    private Set<Field> fields;
    @OneToMany(mappedBy = "questionnaire")
    @ToString.Exclude
    private List<Response> responses;

}