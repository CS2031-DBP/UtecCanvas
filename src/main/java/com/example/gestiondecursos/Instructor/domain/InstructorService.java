package com.example.gestiondecursos.Instructor.domain;

import com.example.gestiondecursos.Instructor.dto.InstructorResponseDTO;
import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final ModelMapper modelMapper;


    public List<InstructorResponseDTO> getAllInstructors(){
        List<Instructor> instructors = instructorRepository.findAll();
        List<InstructorResponseDTO> instructorResponseDTOS = instructors.stream().map(instructor -> modelMapper.map(instructor, InstructorResponseDTO.class)).collect(Collectors.toList());
        return instructorResponseDTOS;
    }

    public InstructorResponseDTO getInstructorByNameAndLastname(String name, String lastname){
        Instructor instructor = instructorRepository.findByNameAndLastname(name, lastname).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        InstructorResponseDTO instructorResponseDTO = modelMapper.map(instructor, InstructorResponseDTO.class);
        return instructorResponseDTO;
    }

    public List<InstructorResponseDTO> getInstructorsByName(String name){
        List<Instructor> instructors = instructorRepository.findByName(name);
        List<InstructorResponseDTO> instructorResponseDTOS = instructors.stream().map(instructor -> modelMapper.map(instructor, InstructorResponseDTO.class)).collect(Collectors.toList());
        return instructorResponseDTOS;
    }

    public InstructorResponseDTO getByEmail(String email){
        Instructor instructor = instructorRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        InstructorResponseDTO instructorResponseDTO = modelMapper.map(instructor, InstructorResponseDTO.class);
        return instructorResponseDTO;
    }

    public void updateInstructor(Long id, Instructor instructor){
        Instructor instructor1 = instructorRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        if(instructor.getDescription() != null){
            instructor1.setDescription(instructor.getDescription());
        }
        if(instructor.getProfilePhoto() != null){
            instructor1.setProfilePhoto(instructor.getProfilePhoto());
        }
        instructorRepository.save(instructor1);
    }

    public void deleteInstructor(Long id){
        Instructor instructor1 = instructorRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        instructorRepository.delete(instructor1);
    }
}
