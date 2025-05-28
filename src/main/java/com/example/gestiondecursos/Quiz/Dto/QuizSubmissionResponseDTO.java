package com.example.gestiondecursos.Quiz.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizSubmissionResponseDTO {
    private Long id;
    private Long quizId;
    private Long studentId;
    private Double score;
    private LocalDateTime submittedAt;
}
