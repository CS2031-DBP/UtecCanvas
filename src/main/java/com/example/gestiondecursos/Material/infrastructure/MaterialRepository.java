package com.example.gestiondecursos.Material.infrastructure;

import com.example.gestiondecursos.Material.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
