package com.example.gestiondecursos.Enrollment.domain;

import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Enrollment.infrastructure.EnrollmentRepository;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.Student.infrastructure.StudentRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public void createEnrollment(Long courseId, String studentEmail){
        Enrollment enrollment = new Enrollment();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFound("Course not found"));
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(() -> new ResourceNotFound("Student not found"));
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrolled(LocalDateTime.now());
//        enrollment.setMaxScore(0);
        enrollmentRepository.save(enrollment);
    }

    public Enrollment getEnrollmentById(Long id){
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Enrollment not found"));
        return enrollment;
    }

    public void removeEnrollment(Long courseId, String studentEmail){
        //Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Enrollment not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFound("Course not found"));
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(() -> new ResourceNotFound("Student not found"));
        Enrollment enrollment = enrollmentRepository.findByCourseAndStudent(course, student).orElseThrow(() -> new ResourceNotFound("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
        course.getEnrollmentList().remove(enrollment);
        student.getEnrollmentList().remove(enrollment);
    }
}
