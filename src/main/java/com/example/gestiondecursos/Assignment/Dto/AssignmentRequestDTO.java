package com.example.gestiondecursos.Assignment.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentRequestDTO {
    @NotNull
    private String title;
    @NotNull
    private Double maxScore;
    @NotNull
    private String instructions;
    private LocalDateTime dueDate;
    private String material;
    private Boolean uploadRequired;
}
