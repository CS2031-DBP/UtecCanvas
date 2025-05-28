package com.example.gestiondecursos.Quiz.infrastructure;

import com.example.gestiondecursos.Quiz.domain.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
}
