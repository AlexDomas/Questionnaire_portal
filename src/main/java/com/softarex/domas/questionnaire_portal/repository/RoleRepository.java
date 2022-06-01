package com.softarex.domas.questionnaire_portal.repository;


import com.softarex.domas.questionnaire_portal.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);
}
