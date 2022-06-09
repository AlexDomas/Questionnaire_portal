package com.softarex.domas.questionnaire_portal.entity.field;

import com.softarex.domas.questionnaire_portal.entity.BaseEntity;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fields")
public class Field extends BaseEntity {

        private boolean isActive;

        private boolean required;

        private String label;

        private Integer position;

        private FieldType fieldType;

        @OneToMany(fetch = FetchType.EAGER, mappedBy = "field")
        private Set<FieldOption> options;
        @ManyToOne
        @JoinColumn(name = "questionnaire_id")
        private Questionnaire questionnaire;


}
