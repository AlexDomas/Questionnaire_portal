package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.questionnaire.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {

    Optional<Questionnaire> findByUser_Email(String email);

    Optional<Questionnaire> findByUser_Id(UUID uuid);

}