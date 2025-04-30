package com.example.gestiondecursos.Instructor.infrastructure;

import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.User.infrastructure.BaseUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface InstructorRepository extends BaseUserRepository<Instructor> {

}
