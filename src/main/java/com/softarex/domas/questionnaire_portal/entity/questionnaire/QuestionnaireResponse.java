package com.softarex.domas.questionnaire_portal.entity.questionnaire;


import com.softarex.domas.questionnaire_portal.entity.BaseEntity;
import com.softarex.domas.questionnaire_portal.entity.field.FieldResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "questionnaire_response")
public class QuestionnaireResponse extends BaseEntity {

    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;
    @OneToMany(mappedBy = "response")
    private List<FieldResponse> concreteResponses;


}
