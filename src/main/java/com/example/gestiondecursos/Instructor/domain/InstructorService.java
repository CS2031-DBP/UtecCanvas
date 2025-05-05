package com.example.gestiondecursos.Instructor.domain;

import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public List<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }

    public Instructor getInstructorByNameAndLastname(String name, String lastname){
        Instructor instructor = instructorRepository.findByNameAndLastname(name, lastname).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        return instructor;
    }

    public List<Instructor> getInstructorsByName(String name){
        List<Instructor> instructors = instructorRepository.findByName(name);
        return instructors;
    }

    public Instructor getByEmail(String email){
        Instructor instructor = instructorRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        return instructor;
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
