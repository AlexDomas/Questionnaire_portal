package com.softarex.domas.questionnaire_portal.controller;

import com.softarex.domas.questionnaire_portal.dto.FieldDto;
import com.softarex.domas.questionnaire_portal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/{id}")
    public FieldDto getField(Principal principal, @PathVariable Integer id) {
        return fieldService.getFieldDto(principal, id);
    }

    @PostMapping
    public FieldDto create(@Valid @RequestBody FieldDto fieldDto, Principal principal) {
        return fieldService.save(principal, fieldDto);
    }

    @DeleteMapping("/{id}")
    public FieldDto delete(@PathVariable Integer id, Principal principal) {
        FieldDto deletedField = fieldService.delete(principal, id);
        return deletedField;
    }

    @PutMapping("/{id}")
    public FieldDto update(@PathVariable Integer id, @Valid @RequestBody FieldDto fieldDto, Principal principal) {
        return fieldService.update(principal, id, fieldDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllFields(Pageable pageable, Principal principal) {
        return ResponseEntity.ok(fieldService.findAllByUserEmail(principal, pageable));
    }

}