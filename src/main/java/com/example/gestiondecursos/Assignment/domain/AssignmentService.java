package com.example.gestiondecursos.Assignment.domain;

import com.example.gestiondecursos.Assignment.Dto.AssignmentRequestDTO;
import com.example.gestiondecursos.Assignment.Dto.AssignmentResponseDTO;
import com.example.gestiondecursos.Assignment.infrastructure.AssignmentRepository;
import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Enrollment.infrastructure.EnrollmentRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.domain.UserService;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;

    public AssignmentResponseDTO createAssignment(Long courseId, AssignmentRequestDTO assignment){
        User user = userService.getAuthenticatedUser();

        if (user instanceof Instructor) {
            boolean ownsCourse = courseRepository.existsByIdAndInstructorId(courseId, user.getId());
            if (!ownsCourse) {
                throw new AccessDeniedException("Instructor not authorized for this course");
            }
        }
        if (!(user instanceof Instructor)) {
            throw new AccessDeniedException("Only instructors can create assignments");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFound("Course not found"));

        Assignment newAssignment = new Assignment();
        newAssignment.setCourse(course);
        newAssignment.setTitle(assignment.getTitle());
        newAssignment.setMaxScore(assignment.getMaxScore());
        newAssignment.setInstructions(assignment.getInstructions());
        newAssignment.setCreatedAt(LocalDateTime.now());
        newAssignment.setDueDate(assignment.getDueDate());
        newAssignment.setMaterial(assignment.getMaterial());
        newAssignment.setUploadRequired(assignment.getUploadRequired());

        Assignment saved = assignmentRepository.save(newAssignment);
        course.getEvaluations().add(saved);

        return modelMapper.map(saved, AssignmentResponseDTO.class);
    }


    public void updateAssignment(Long id, AssignmentRequestDTO assignment){
        Assignment assignment1 = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Assignment not found"));
        validateInstructorAccessToAssignment(assignment1);
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
        validateInstructorAccessToAssignment(assignment);
        assignmentRepository.delete(assignment);
    }

    public AssignmentResponseDTO getByTitle(String title){
        Assignment assignment = assignmentRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFound("Assignment not found"));

        User user = userService.getAuthenticatedUser();
        Long courseId = assignment.getCourse().getId();

        if (user instanceof Student) {
            boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseId(user.getId(), courseId);
            if (!enrolled) {
                throw new AccessDeniedException("Student not enrolled in this course");
            }
        } else if (user instanceof Instructor) {
            boolean ownsCourse = courseRepository.existsByIdAndInstructorId(courseId, user.getId());
            if (!ownsCourse) {
                throw new AccessDeniedException("Instructor not authorized");
            }
        }

        return modelMapper.map(assignment, AssignmentResponseDTO.class);
    }


    private void validateInstructorAccessToAssignment(Assignment assignment) {
        User user = userService.getAuthenticatedUser();
        if (user instanceof Instructor) {
            if (!assignment.getCourse().getInstructor().getId().equals(user.getId())) {
                throw new AccessDeniedException("Instructor not authorized to modify this assignment");
            }
        } else {
            throw new AccessDeniedException("Only instructors can modify assignments");
        }
    }

}
