package ish.ishmael.zeraki.service;


;
import ish.ishmael.zeraki.model.Course;
import ish.ishmael.zeraki.model.Institution;
import ish.ishmael.zeraki.respository.CourseRepository;
import ish.ishmael.zeraki.respository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CourseService {

    private final CourseRepository courseRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }



    public Course addCourse(Course course) {
        Institution institution = institutionRepository.findById(course.getInstitution().getId())
                .orElseThrow(() -> new EntityNotFoundException("Institution with ID " + course.getInstitution().getId() + " does not exist."));
        // Check if the institution exists
        Long institutionId = course.getInstitution().getId();
        boolean institutionExists = institutionRepository.existsById(institutionId);

        if (!institutionExists) {
            throw new IllegalStateException("Institution with ID " + institutionId + " does not exist.");
        }
        Optional<Course> existingCourse = courseRepository
                .findByInstitutionIdAndName(course.getInstitution().getId(), course.getName());
        if (existingCourse.isPresent()) {
            throw new IllegalStateException("Course with the same name already exists in this institution.");
        }
        // Create a new Course instance and set properties
        Course courseData = new Course();
        courseData.setName(course.getName());
        courseData.setInstitution(institution); // Associate the course with the retrieved institution

        // Save the new course
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
        course.setName(courseDetails.getName());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
        courseRepository.delete(course);
    }

    public List<Course> getCoursesByInstitutionId(Long institutionId) {
        return courseRepository.findByInstitutionId(institutionId);
    }
}

