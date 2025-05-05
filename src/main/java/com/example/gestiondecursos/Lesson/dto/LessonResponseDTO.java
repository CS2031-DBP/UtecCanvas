package com.example.gestiondecursos.Lesson.dto;

import lombok.Data;

import java.util.List;

@Data
public class LessonResponseDTO{
    private Long id;
    private String title;
    private Integer week;
    private String courseTitle;
    private List<String> materialTitles;
}
