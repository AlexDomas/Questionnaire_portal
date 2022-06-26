package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import com.softarex.domas.questionnaire_portal.entity.questionnaire.QuestionnaireResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionnaireResponseRepository extends JpaRepository<QuestionnaireResponse, UUID> {

    Page<QuestionnaireResponse> findAllByQuestionnaireOrderByCreationDate(Questionnaire questionnaire, Pageable pageable);

}
