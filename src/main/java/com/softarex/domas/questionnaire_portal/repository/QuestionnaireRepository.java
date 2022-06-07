package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {

}
