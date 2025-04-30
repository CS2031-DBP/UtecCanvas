package com.example.gestiondecursos.Student.infrastructure;

import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.User.infrastructure.BaseUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface StudentRepository extends BaseUserRepository<Student> {

}
