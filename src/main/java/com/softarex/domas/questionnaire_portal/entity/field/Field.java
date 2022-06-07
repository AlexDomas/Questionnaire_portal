package com.softarex.domas.questionnaire_portal.entity.field;

import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Field {
        @Id
        @GeneratedValue
        private UUID id;
        @Column(name = "is_active")
        private boolean isActive;
        private boolean required;
        private String label;
        private FieldType fieldType;
        @OneToMany(mappedBy = "field")
        @ToString.Exclude
        private List<Response> responses;
        @OneToMany(mappedBy = "field")
        @ToString.Exclude
        private Set<FieldOption> options;
        @ManyToOne
        @JoinColumn(name = "questionnaire_id")
        @ToString.Exclude
        private Questionnaire questionnaire;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Field field)) return false;
            if (isActive != field.isActive) return false;
            if (required != field.required) return false;
            if (!Objects.equals(id, field.id)) return false;
            if (!Objects.equals(label, field.label)) return false;
            return fieldType == field.fieldType;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (isActive ? 1 : 0);
            result = 31 * result + (required ? 1 : 0);
            result = 31 * result + (label != null ? label.hashCode() : 0);
            result = 31 * result + (fieldType != null ? fieldType.hashCode() : 0);
            return result;
        }
}
