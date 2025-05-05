package com.example.gestiondecursos.Question.application;

import com.example.gestiondecursos.Question.domain.Question;
import com.example.gestiondecursos.Question.domain.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/quizId/{id}")
    public ResponseEntity<Void> createQuestion(@PathVariable Long id, @RequestBody Question question){
        questionService.createQuestion(id, question);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/questionId/{id}")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long id, @RequestBody Question question){
        questionService.updateQuestion(id, question);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/questionId/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
