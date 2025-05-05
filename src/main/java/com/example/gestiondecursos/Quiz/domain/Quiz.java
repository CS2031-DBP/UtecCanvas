package com.example.gestiondecursos.Quiz.domain;

import com.example.gestiondecursos.Evaluation.domain.Evaluation;
import com.example.gestiondecursos.Question.domain.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "Quiz_table")
public class Quiz extends Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();
}
