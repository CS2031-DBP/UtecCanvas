package com.example.gestiondecursos.Assignment.infrastructure;

import com.example.gestiondecursos.Assignment.domain.Assignment;
import com.example.gestiondecursos.Evaluation.infrastructure.BaseEvaluationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface AssignmentRepository extends BaseEvaluationRepository<Assignment> {
}
