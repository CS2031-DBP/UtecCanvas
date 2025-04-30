package com.example.gestiondecursos.Quiz.domain;

import com.example.gestiondecursos.Evaluation.domain.Evaluation;
import com.example.gestiondecursos.Question.domain.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();
}
