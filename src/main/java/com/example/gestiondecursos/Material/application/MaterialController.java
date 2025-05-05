package com.example.gestiondecursos.Material.application;

import com.example.gestiondecursos.Material.domain.Material;
import com.example.gestiondecursos.Material.domain.MaterialService;
import com.example.gestiondecursos.Material.dto.MaterialResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping("/week/{week}")
    public ResponseEntity<MaterialResponseDTO> createMaterial(@PathVariable Integer week, @RequestBody Material material1){
        MaterialResponseDTO material = materialService.createMaterial(week, material1);
        return ResponseEntity.status(HttpStatus.CREATED).body(material);
    }
}
