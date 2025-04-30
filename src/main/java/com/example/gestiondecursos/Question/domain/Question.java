package com.example.gestiondecursos.Question.domain;


import com.example.gestiondecursos.Evaluation.domain.Evaluation;
import com.example.gestiondecursos.Quiz.domain.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "question_table")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Question{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "quiz_idd")
    private Quiz quiz;
}
