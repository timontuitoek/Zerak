package ish.ishmael.zeraki.controller;

import ish.ishmael.zeraki.model.Institution;
import ish.ishmael.zeraki.service.InstitutionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    // Add a new institution with validation
    @PostMapping
    public ResponseEntity<?> addInstitution(@Validated @RequestBody Institution institution) {
        Institution newInstitution = institutionService.addInstitution(institution);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstitution);
    }

    // List all institutions
    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        if (institutions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(institutions);
    }

    // Get an institution by ID with improved error handling
    @GetMapping("/{id}")
    public ResponseEntity<?> getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id)
                .map(institution -> ResponseEntity.ok().body(institution))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an institution with validation
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInstitution(@PathVariable Long id, @Validated @RequestBody Institution institution) {
        try {
            Institution updatedInstitution = institutionService.updateInstitution(id, institution);
            return ResponseEntity.ok(updatedInstitution);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete an institution with improved response for error handling
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstitution(@PathVariable Long id) {
        try {
            institutionService.deleteInstitution(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
