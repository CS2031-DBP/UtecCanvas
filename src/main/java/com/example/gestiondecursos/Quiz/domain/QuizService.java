package com.example.gestiondecursos.Quiz.domain;

import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Quiz.infrastructure.QuizRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;

    public Quiz createQuiz(Long id, Quiz quiz){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Course not found"));
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quiz.getTitle());
        newQuiz.setMaxScore(quiz.getMaxScore());
        newQuiz.setInstructions(quiz.getInstructions());
        newQuiz.setCreatedAt(LocalDateTime.now());
        newQuiz.setDueDate(quiz.getDueDate());
        newQuiz.setCourse(course);
        quizRepository.save(newQuiz);
        course.getEvaluations().add(newQuiz);
        return newQuiz;
    }


}
