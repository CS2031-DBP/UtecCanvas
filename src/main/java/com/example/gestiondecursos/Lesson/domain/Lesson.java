package com.example.gestiondecursos.Lesson.domain;

import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Evaluation.domain.Evaluation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Lesson_table")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String material;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Evaluation> evaluations = new ArrayList<>();
}
