package com.example.gestiondecursos.Enrollment.application;

import com.example.gestiondecursos.Enrollment.domain.Enrollment;
import com.example.gestiondecursos.Enrollment.domain.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/courseId/{id}/studentEmail/{email}")
    public ResponseEntity<Void> createdEnrollment(@PathVariable Long id, @PathVariable String email){
        enrollmentService.createEnrollment(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Enrollment> getById(@PathVariable Long id){
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(enrollment);
    }

    @DeleteMapping("/courseId/{courseId}/studentEmail/{email}")
    public ResponseEntity<Void> removeEnrollment(@PathVariable Long id, @PathVariable String email){
        enrollmentService.removeEnrollment(id, email);
        return ResponseEntity.noContent().build();
    }
}
