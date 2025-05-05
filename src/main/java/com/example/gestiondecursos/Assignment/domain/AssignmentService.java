package com.example.gestiondecursos.Assignment.domain;

import com.example.gestiondecursos.Assignment.infrastructure.AssignmentRepository;
import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public Assignment createAssignment(Long id,Assignment assignment){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
        Assignment newAssignment = new Assignment();
        newAssignment.setCourse(course);
        newAssignment.setTitle(assignment.getTitle());
        newAssignment.setMaxScore(assignment.getMaxScore());
        newAssignment.setInstructions(assignment.getInstructions());
        newAssignment.setCreatedAt(LocalDateTime.now());
        newAssignment.setDueDate(assignment.getDueDate());
        newAssignment.setMaterial(assignment.getMaterial());
        newAssignment.setUploadRequired(assignment.getUploadRequired());
        Assignment assignment1 = assignmentRepository.save(newAssignment);
        course.getEvaluations().add(newAssignment);
        return assignment1;
    }

    public void updateAssignment(Long id, Assignment assignment){
        Assignment assignment1 = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Assignment not found"));
        if(assignment.getTitle() != null){
            assignment1.setTitle(assignment.getTitle());
        }
        if(assignment.getMaxScore() != null) {
            assignment1.setMaxScore(assignment.getMaxScore());
        }
        if(assignment.getInstructions() != null){
            assignment1.setInstructions(assignment.getInstructions());
        }
        if(assignment.getDueDate() != null){
            assignment1.setDueDate(assignment.getDueDate());
        }
        if(assignment.getMaterial() != null){
            assignment1.setMaterial(assignment.getMaterial());
        }
        if(assignment.getUploadRequired() != null){
            assignment1.setUploadRequired(assignment.getUploadRequired());
        }
        assignmentRepository.save(assignment1);
    }

    public void deleteAssignment(Long id){
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Assignment not found"));
        assignmentRepository.delete(assignment);
    }

    public Assignment getByTitle(String title){
        Assignment assignment = assignmentRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFound("Assignment not found"));
        return assignment;
    }
}
