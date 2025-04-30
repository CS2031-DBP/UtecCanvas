package com.example.gestiondecursos.User.infrastructure;

import com.example.gestiondecursos.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository <T extends User> extends JpaRepository<T, Long>{
}
