package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.field.Field;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {

    List<Field> findAllByQuestionnaireIdOrderByPositionAsc(UUID questionnaireId);

    Page<Field> findAllByQuestionnaireIdOrderByPositionAsc(UUID questionnaireId, Pageable pageable);

    Integer countAllByQuestionnaire(Questionnaire questionnaire);

}
