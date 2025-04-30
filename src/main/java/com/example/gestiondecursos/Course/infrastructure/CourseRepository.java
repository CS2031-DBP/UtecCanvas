package com.example.gestiondecursos.Course.infrastructure;

import com.example.gestiondecursos.Course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
