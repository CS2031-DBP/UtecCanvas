package com.example.gestiondecursos.Material.application;

import com.example.gestiondecursos.Material.domain.Material;
import com.example.gestiondecursos.Material.domain.MaterialService;
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
    public ResponseEntity<Material> createMaterial(@PathVariable Integer week, @RequestBody Material material1){
        Material material = materialService.createMaterial(week, material1);
        return ResponseEntity.status(HttpStatus.CREATED).body(material);
    }
}
