package com.example.gestiondecursos.Assignment.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentResponseDTO {
    private Long id;
    private String title;
    private Integer maxScore;
    private String instructions;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private Boolean uploadRequired;
}
