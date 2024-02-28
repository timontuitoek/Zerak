package ish.ishmael.zeraki.service;

import ish.ishmael.zeraki.model.Course;
import ish.ishmael.zeraki.model.Institution;
import ish.ishmael.zeraki.model.Student;
import ish.ishmael.zeraki.respository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByCourseId(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }

    public Student updateStudent(Long id,Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with " + id));
        student.setName(studentDetails.getName());
        return studentRepository.save(student);
    }
}
