package com.example.gestiondecursos.Lesson.application;

import com.example.gestiondecursos.Lesson.domain.Lesson;
import com.example.gestiondecursos.Lesson.domain.LessonService;
import com.example.gestiondecursos.Lesson.dto.LessonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/courseId/{id}")
    public ResponseEntity<Void> createdLesson(@PathVariable Long id, @RequestBody Lesson lesson){
        lessonService.createLesson(id, lesson);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<LessonResponseDTO> getLessonByTitle(@PathVariable String title){
        LessonResponseDTO lesson = lessonService.getLessonByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @GetMapping("/getByWeek/{week}")
    public ResponseEntity<LessonResponseDTO> getLessonByWeek(@PathVariable Integer week){
        LessonResponseDTO lesson = lessonService.getLessonByWeek(week);
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id){
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/lessonId/{id}")
    public ResponseEntity<Void> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson){
        lessonService.updateLesson(id, lesson);
        return ResponseEntity.noContent().build();
    }
}
