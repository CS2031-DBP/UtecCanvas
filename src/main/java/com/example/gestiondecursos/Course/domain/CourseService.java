package com.example.gestiondecursos.Course.domain;

import com.example.gestiondecursos.Course.dto.CourseRequestDTO;
import com.example.gestiondecursos.Course.dto.CourseRequestForUpdateDTO;
import com.example.gestiondecursos.Course.dto.CourseResponseDTO;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.exceptions.ResourceIsNullException;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final ModelMapper modelMapper;

    public CourseResponseDTO getById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course Not Found"));
        CourseResponseDTO courseResponseDTO = modelMapper.map(course, CourseResponseDTO.class);

        Instructor instructor = course.getInstructor();
        if(instructor == null){
            throw new ResourceIsNullException("There is not Instructor assigned to this course");
        }
        if(course.getInstructor().getName() == null){
            throw new ResourceIsNullException("There is not Instructor name to link");
        }
        if(course.getInstructor().getLastname() == null){
            throw new ResourceIsNullException("There is not Instructor lastname to link");
        }
        courseResponseDTO.setInstructorName(course.getInstructor().getName());
        courseResponseDTO.setInstructorLastname(course.getInstructor().getLastname());
        return courseResponseDTO;
    }

    public void createCourse(CourseRequestDTO course){
        Course newCourse = new Course();
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setSection(course.getSection());
        newCourse.setCategory(course.getCategory());
        courseRepository.save(newCourse);
    }

    public void updateCourse(Long id, CourseRequestForUpdateDTO course){
        Course course1 = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
        if(course.getTitle() != null){
            course1.setTitle(course.getTitle());
        }
        if(course.getDescription() != null){
            course1.setDescription(course.getDescription());
        }
        if(course.getSection() != null){
            course1.setSection(course.getSection());
        }
        if(course.getCategory() != null){
            course1.setCategory(course.getCategory());
        }
        courseRepository.save(course1);
    }

    public List<CourseResponseDTO> getAllByTitle(String title){
        List<Course> courseList = courseRepository.findAllByTitle(title);
        return courseList.stream()
                .map(course -> {
                    CourseResponseDTO courseResponseDTO = modelMapper.map(course, CourseResponseDTO.class);
                    courseResponseDTO.setInstructorName(course.getInstructor().getName());
                    courseResponseDTO.setInstructorLastname(course.getInstructor().getLastname());
                    return courseResponseDTO;
                }).collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseByTitleAndCategory(String title, String category){
        Course course = courseRepository.findCourseByTitleAndCategory(title, category).orElseThrow(() -> new ResourceNotFound("Course not found"));
        CourseResponseDTO courseResponseDTO = modelMapper.map(course, CourseResponseDTO.class);
        courseResponseDTO.setInstructorName(course.getInstructor().getName());
        return courseResponseDTO;
    }

    public CourseResponseDTO getCourseByTitleAndSection(String title, String section){
        Course course = courseRepository.findCourseByTitleAndSection(title, section).orElseThrow(() -> new ResourceNotFound("Course not found"));
        CourseResponseDTO courseResponseDTO = modelMapper.map(course, CourseResponseDTO.class);
        return courseResponseDTO;
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
