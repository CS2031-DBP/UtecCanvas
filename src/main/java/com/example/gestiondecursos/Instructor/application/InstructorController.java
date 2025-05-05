package com.example.gestiondecursos.Instructor.application;

import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.domain.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Instructor>> allInstructors(){
        List<Instructor> instructor = instructorService.getAllInstructors();
        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }

    @GetMapping("/name/{name}/lastname/{lastname}")
    public ResponseEntity<Instructor> getByFullname(@PathVariable String name, @PathVariable String lastname){
        Instructor instructor = instructorService.getInstructorByNameAndLastname(name, lastname);
        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }

    @GetMapping("/getAllByName/{name}")
    public ResponseEntity<List<Instructor>> getByName(@PathVariable String name){
        List<Instructor> instructors = instructorService.getInstructorsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Instructor> getByEmail(@PathVariable String email){
        Instructor instructor = instructorService.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }

    @PatchMapping("/instructorId/{id}")
    public ResponseEntity<Void> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor){
        instructorService.updateInstructor(id, instructor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id){
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

}
