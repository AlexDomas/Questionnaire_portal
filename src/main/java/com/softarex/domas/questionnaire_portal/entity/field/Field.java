package com.softarex.domas.questionnaire_portal.entity.field;

import com.softarex.domas.questionnaire_portal.entity.BaseEntity;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "fields")
public class Field extends BaseEntity {

        @Column(name = "is_active")
        private boolean isActive;
        private boolean required;
        private String label;
        private FieldType fieldType;
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "field")
        @ToString.Exclude
        private List<Response> responses;
        @OneToMany(mappedBy = "field")
        @ToString.Exclude
        private Set<FieldOption> options;
        @ManyToOne
        @JoinColumn(name = "questionnaire_id")
        @ToString.Exclude
        private Questionnaire questionnaire;


}
