package com.example.gestiondecursos.Course.application;

import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.domain.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id){
        Course course = courseService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @PostMapping
    public ResponseEntity<Void> createCourse(@RequestBody Course course){
        courseService.createCourse(course);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/courseId/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable Long id, @RequestBody Course course){
        courseService.updateCourse(id, course);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<List<Course>> getAllByTitle(@PathVariable String title){
        List<Course> course = courseService.getAllByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @GetMapping("/getByTitle/{title}/category/{category}")
    public ResponseEntity<Course> getByTitleAndCategory(@PathVariable String title, @PathVariable String category){
        Course course = courseService.getCourseByTitleAndCategory(title, category);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @GetMapping("/getByTitle/{title}/section/{section}")
    public ResponseEntity<Course> getByTitleAndSection(@PathVariable String title, @PathVariable String section){
        Course course = courseService.getCourseByTitleAndSection(title, section);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @DeleteMapping("/courseId/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/instructor/{email}/courseId/{id}")
    public ResponseEntity<Void> assignInstructor(@PathVariable String email, @PathVariable Long id){
        courseService.assignInstructor(email, id);
        return ResponseEntity.noContent().build();
    }
}
