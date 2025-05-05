package com.example.gestiondecursos.Material.domain;

import com.example.gestiondecursos.Lesson.domain.Lesson;
import com.example.gestiondecursos.Lesson.infrastructure.LessonRepository;
import com.example.gestiondecursos.Material.dto.MaterialResponseDTO;
import com.example.gestiondecursos.Material.infrastructure.MaterialRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    public MaterialResponseDTO createMaterial(Integer week, Material material){
        Lesson lesson = lessonRepository.findByWeek(week).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        Material newMaterial = new Material();
        newMaterial.setTitle(material.getTitle());
        newMaterial.setLesson(lesson);
        newMaterial.setType(material.getType());
        newMaterial.setUrl(material.getUrl());
        materialRepository.save(newMaterial);
        MaterialResponseDTO materialResponseDTO = modelMapper.map(newMaterial, MaterialResponseDTO.class);
        return materialResponseDTO;
    }

}