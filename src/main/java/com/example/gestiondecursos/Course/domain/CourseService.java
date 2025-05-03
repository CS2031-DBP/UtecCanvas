package com.example.gestiondecursos.Course.domain;

import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public Course getById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course Not Found"));
        return course;
    }

    public void createCourse(Course course, Long id){
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        Course newCourse = new Course();
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setInstructor(instructor);
    }
}
