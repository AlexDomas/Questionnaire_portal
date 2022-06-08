package com.softarex.domas.questionnaire_portal.entity.field;

import com.softarex.domas.questionnaire_portal.entity.BaseEntity;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.QuestionnaireResponse;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "field_responses")
public class FieldResponse extends BaseEntity {

    private String value;
    private Integer position;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_response_id")
    private QuestionnaireResponse response;

}
