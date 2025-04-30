package com.example.gestiondecursos.Evaluation.domain;

import com.example.gestiondecursos.Lesson.domain.Lesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "Evaluation_table")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Integer maxScore;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
