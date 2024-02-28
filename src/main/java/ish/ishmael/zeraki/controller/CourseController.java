package ish.ishmael.zeraki.controller;

import ish.ishmael.zeraki.model.Course;
import ish.ishmael.zeraki.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // List all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // Get courses by institution ID
    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<List<Course>> getCoursesByInstitution(@PathVariable Long institutionId) {
        List<Course> courses = courseService.getCoursesByInstitutionId(institutionId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // Add a new course to an institution
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            Course newCourse = courseService.addCourse(course);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Edit an existing course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}