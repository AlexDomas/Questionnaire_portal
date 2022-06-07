package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.field.FieldOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOption, UUID> {

}
