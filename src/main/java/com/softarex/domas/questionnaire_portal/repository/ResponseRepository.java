package com.softarex.domas.questionnaire_portal.repository;

import com.softarex.domas.questionnaire_portal.entity.response.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Response, UUID> {

}
