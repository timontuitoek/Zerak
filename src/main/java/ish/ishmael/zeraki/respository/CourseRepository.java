package ish.ishmael.zeraki.respository;

import ish.ishmael.zeraki.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find courses by the institution id
    List<Course> findByInstitutionId(Long institutionId);

    // Check for the existence of a course with a given name within an institution
    Optional<Course> findByInstitutionIdAndName(Long institutionId, String name);

    long countByInstitutionId(Long institutionId);

}