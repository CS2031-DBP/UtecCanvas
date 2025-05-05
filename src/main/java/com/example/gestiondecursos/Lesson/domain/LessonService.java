package com.example.gestiondecursos.Lesson.domain;


import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Lesson.infrastructure.LessonRepository;
import com.example.gestiondecursos.Material.domain.Material;
import com.example.gestiondecursos.Material.domain.MaterialService;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final MaterialService materialService;

    public void createLesson(Long id, Lesson lesson){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
        Lesson lesson1 = new Lesson();
        lesson1.setWeek(lesson.getWeek());
        lesson1.setTitle(lesson.getTitle());
        lesson1.setCourse(course);
        //lesson1.setContent(lesson.getContent());
        lessonRepository.save(lesson1);
    }

    public Lesson getLessonByTitle(String title){
        Lesson lesson = lessonRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        return lesson;
    }

    public Lesson getLessonByWeek(Integer week){
        Lesson lesson = lessonRepository.findByWeek(week).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        return lesson;
    }

    public void deleteLesson(Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        lessonRepository.delete(lesson);
    }

    public void updateLesson(Long id, Lesson lesson){
        Lesson lesson1 = lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        if(lesson.getTitle() != null) {
            lesson1.setTitle(lesson.getTitle());
        }
        lessonRepository.save(lesson1);
    }
//    public void assignMaterial(Integer week, Material material){
//        Lesson lesson = getLessonByWeek(week);
//        Material material1 = materialService.createMaterial(material);
//        material1.setLesson(lesson);
//        lesson.getMaterials().add(material1);
//        lessonRepository.save(lesson);
//    }
}
