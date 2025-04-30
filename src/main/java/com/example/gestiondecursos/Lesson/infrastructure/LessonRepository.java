package com.example.gestiondecursos.Lesson.infrastructure;

import com.example.gestiondecursos.Lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
}
