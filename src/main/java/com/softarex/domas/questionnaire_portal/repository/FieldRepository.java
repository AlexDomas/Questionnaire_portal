package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.field.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {

}
