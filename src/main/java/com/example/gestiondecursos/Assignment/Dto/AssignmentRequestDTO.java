package com.example.gestiondecursos.Assignment.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentRequestDTO {
    private String title;
    private Integer maxScore;
    private String instructions;
    private LocalDateTime dueDate;
    private String material;
    private Boolean uploadRequired;
}
