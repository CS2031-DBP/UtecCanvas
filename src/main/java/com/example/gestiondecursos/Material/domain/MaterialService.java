package com.example.gestiondecursos.Material.domain;

import com.example.gestiondecursos.Course.domain.CourseService;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Enrollment.domain.Enrollment;
import com.example.gestiondecursos.Enrollment.infrastructure.EnrollmentRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Lesson.domain.Lesson;
import com.example.gestiondecursos.Lesson.infrastructure.LessonRepository;
import com.example.gestiondecursos.Material.dto.MaterialRequestDTO;
import com.example.gestiondecursos.Material.dto.MaterialResponseDTO;
import com.example.gestiondecursos.Material.infrastructure.MaterialRepository;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.domain.UserService;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;

    public MaterialResponseDTO createMaterial(Long courseId, Integer week, MaterialRequestDTO material){
        validateUserAccessToCourse(courseId);

        Lesson lesson = lessonRepository.findByCourseIdAndWeek(courseId, week)
                .orElseThrow(() -> new ResourceNotFound("Lesson not found"));

        Material newMaterial = new Material();
        newMaterial.setTitle(material.getTitle());
        newMaterial.setLesson(lesson);
        newMaterial.setType(material.getType());
        newMaterial.setUrl(material.getUrl());

        materialRepository.save(newMaterial);
        return modelMapper.map(newMaterial, MaterialResponseDTO.class);
    }

    private void validateUserAccessToCourse(Long courseId) {
        User user = userService.getAuthenticatedUser();

        if (user instanceof Student) {
            boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseId(user.getId(), courseId);
            if (!enrolled) {
                throw new AccessDeniedException("Student not enrolled in this course");
            }
        }

        if (user instanceof Instructor) {
            boolean ownsCourse = courseRepository.existsByIdAndInstructorId(courseId, user.getId());
            if (!ownsCourse) {
                throw new AccessDeniedException("Instructor does not teach this course");
            }
        }
    }

}