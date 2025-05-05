package com.example.gestiondecursos.Course.dto;

import lombok.Data;

@Data
public class CourseRequestDTO {
    private String title;
    private String description;
    private String section;
    private String category;
}
