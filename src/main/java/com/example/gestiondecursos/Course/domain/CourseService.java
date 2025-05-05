package com.example.gestiondecursos.Course.domain;

import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public Course getById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course Not Found"));
        return course;
    }

    public void createCourse(Course course){
        Course newCourse = new Course();
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setSection(course.getSection());
        newCourse.setDescription(course.getDescription());
        courseRepository.save(newCourse);
    }

    public void updateCourse(Long id, Course course){
        Course course1 = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
//        course1.setTitle(course.getTitle());
//        course1.setDescription(course.getDescription());
//        course1.setSection(course.getSection());
        if(course.getTitle() != null){
            course1.setTitle(course.getTitle());
        }
        if(course.getDescription() != null){
            course1.setDescription(course.getDescription());
        }
        if(course.getSection() != null){
            course1.setSection(course.getSection());
        }
        courseRepository.save(course1);
    }

    public List<Course> getAllByTitle(String title){
        List<Course> courseList = courseRepository.findAllByTitle(title);
        return courseList;
    }

    public Course getCourseByTitleAndCategory(String title, String category){
        Course course = courseRepository.findCourseByTitleAndCategory(title, category).orElseThrow(() -> new ResourceNotFound("Course not found"));
        return course;
    }

    public Course getCourseByTitleAndSection(String title, String section){
        Course course = courseRepository.findCourseByTitleAndSection(title, section).orElseThrow(() -> new ResourceNotFound("Course not found"));
        return course;
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
        courseRepository.delete(course);
    }

    public void assignInstructor(String email, Long id){
        Instructor instructor = instructorRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Teacher not found"));
        Course currentCourse = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course Not Found"));
        currentCourse.setInstructor(instructor);
        instructor.getCourseList().add(currentCourse);
        courseRepository.save(currentCourse);
    }


}
