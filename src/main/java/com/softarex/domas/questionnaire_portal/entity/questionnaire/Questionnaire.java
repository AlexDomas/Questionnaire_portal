package com.softarex.domas.questionnaire_portal.entity.questionnaire;

import com.softarex.domas.questionnaire_portal.entity.BaseEntity;
import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "questionnaire")
public class Questionnaire extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "questionnaire")
    private Set<Field> fields;

    @OneToMany(mappedBy = "questionnaire")
    private List<QuestionnaireResponse> questionnaireResponses;

}