package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.FieldResponseDto;
import com.softarex.domas.questionnaire_portal.service.FieldResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/responses")
public class QuestionnaireResponseController {

    private final FieldResponseService fieldResponseService;

    @PostMapping("/{userId}")
    public List<FieldResponseDto> create(
            @Valid @RequestBody List<FieldResponseDto> responses,
            @PathVariable(name = "userId") UUID userId) {
        return fieldResponseService.saveAll(responses, userId);
    }

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable, Principal principal) {
        return ResponseEntity.ok(fieldResponseService.findAllByUserId(principal, pageable));
    }

}
