package com.example.gestiondecursos.Quiz.Dto;

import lombok.Data;

@Data
public class QuizResponseDTO {
    private Long id;
    private String title;
    private Integer questionCount;
}
