package com.example.gestiondecursos.Quiz.application;

import com.example.gestiondecursos.Quiz.Dto.QuizRequestDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizResponseDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizSubmissionDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizSubmissionResponseDTO;
import com.example.gestiondecursos.Quiz.domain.Quiz;
import com.example.gestiondecursos.Quiz.domain.QuizService;
import com.example.gestiondecursos.Quiz.domain.QuizSubmission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping("/courseId/{courseId}")
    public ResponseEntity<Void> createQuiz(@PathVariable Long courseId,@RequestBody @Valid QuizRequestDTO quizRequest) {
        quizService.createQuiz(courseId, quizRequest);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/title/{title}")
    public ResponseEntity<QuizResponseDTO> getQuizByTitle(@PathVariable String title) {
        QuizResponseDTO quizResponseDTO = quizService.getQuizByTitle(title);
        return ResponseEntity.ok(quizResponseDTO);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/submit")
    public ResponseEntity<QuizSubmissionResponseDTO> submitQuiz(@Valid @RequestBody QuizSubmissionDTO submissionDTO) {
        QuizSubmissionResponseDTO responseDTO = quizService.submitQuiz(submissionDTO);
        return ResponseEntity.ok(responseDTO);
    }



    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/delete/id/{quizId}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable Long quizId) {
        quizService.deleteQuizById(quizId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/delete/title/{title}")
    public ResponseEntity<Void> deleteQuizByTitle(@PathVariable String title) {
        quizService.deleteQuizByTitle(title);
        return ResponseEntity.noContent().build();
    }

}

