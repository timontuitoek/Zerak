package ish.ishmael.zeraki.controller;
import ish.ishmael.zeraki.model.Student;
import ish.ishmael.zeraki.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Validated @RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return new ResponseEntity<>(
                    savedStudent,
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Method to get students by course ID, including error handling if the course doesn't exist
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable Long courseId) {
        try {
            List<Student> students = studentService.findStudentsByCourseId(courseId);
            if (students.isEmpty()) {
                return new ResponseEntity<>("No students found for the given course ID.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updatedStudentData(@PathVariable Long studentId,@RequestBody Student student ) {
        try {
            Student student1 = studentService.updateStudent(studentId, student);
            return ResponseEntity.ok(student1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
