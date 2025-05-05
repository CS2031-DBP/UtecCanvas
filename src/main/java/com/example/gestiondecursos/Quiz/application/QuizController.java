package com.example.gestiondecursos.Quiz.application;

import com.example.gestiondecursos.Quiz.domain.Quiz;
import com.example.gestiondecursos.Quiz.domain.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/courseId/{id}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable Long id, @RequestBody Quiz quiz1){
        Quiz quiz = quizService.createQuiz(id, quiz1);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }
}
