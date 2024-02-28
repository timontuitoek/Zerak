package ish.ishmael.zeraki.service;

import ish.ishmael.zeraki.model.Institution;
import ish.ishmael.zeraki.respository.CourseRepository;
import ish.ishmael.zeraki.respository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;


    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Optional<Institution> getInstitutionById(Long id) {
        return institutionRepository.findById(id);
    }

    public Institution addInstitution(Institution institution) {
        // Check if an institution with the same name already exists
        Optional<Institution> existingInstitution = institutionRepository.findByName(institution.getName());
        if (existingInstitution.isPresent()) {
            throw new IllegalStateException("Institution with the same name already exists.");
        }
        return institutionRepository.save(institution);
    }

    @Transactional
    public Institution updateInstitution(Long id, Institution institutionDetails) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Institution with id " + id + " does not exist."));

        String newName = institutionDetails.getName();
        if (newName != null && !newName.isEmpty() && !newName.equals(institution.getName())) {
            // Check if another institution with the new name already exists
            Optional<Institution> institutionWithNewName = institutionRepository.findByName(newName);
            if (institutionWithNewName.isPresent()) {
                throw new IllegalStateException("Institution with the name " + newName + " already exists.");
            }
            institution.setName(newName);
        }
        return institution;
    }

    public void deleteInstitution(Long id) {
        // Check if any courses are associated with the institution
        if (courseRepository.countByInstitutionId(id) > 0) {
            throw new IllegalStateException("Cannot delete institution because it has associated courses.");
        }

        // Proceed with deletion if no courses are found
        if (!institutionRepository.existsById(id)) {
            throw new EntityNotFoundException("Institution with id " + id + " does not exist.");
        }
        institutionRepository.deleteById(id);
    }
}