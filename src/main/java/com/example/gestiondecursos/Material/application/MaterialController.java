package com.example.gestiondecursos.Material.application;

import com.example.gestiondecursos.Material.domain.Material;
import com.example.gestiondecursos.Material.domain.MaterialService;
import com.example.gestiondecursos.Material.dto.MaterialRequestDTO;
import com.example.gestiondecursos.Material.dto.MaterialResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping("/courseId/{courseId}/week/{week}")
    public ResponseEntity<MaterialResponseDTO> createMaterial(@PathVariable Long courseId,@PathVariable Integer week, @RequestBody @Valid MaterialRequestDTO material1){
        MaterialResponseDTO material = materialService.createMaterial(courseId, week, material1);
        return ResponseEntity.status(HttpStatus.CREATED).body(material);
    }
}
