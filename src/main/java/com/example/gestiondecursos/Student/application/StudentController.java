package com.example.gestiondecursos.Student.application;

import com.example.gestiondecursos.Student.Dto.StudentResponseDTO;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.Student.domain.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
     private final StudentService studentService;

     @GetMapping("/getByEmail/{email}")
     public ResponseEntity<StudentResponseDTO> getStudentByEmail(@PathVariable String email){
         StudentResponseDTO student = studentService.getStudentByEmail(email);
         return ResponseEntity.status(HttpStatus.OK).body(student);
     }

     @GetMapping("/getByName/{name}")
     public ResponseEntity<List<StudentResponseDTO>> getStudentByName(@PathVariable String name){
         List<StudentResponseDTO> studentList = studentService.getStudentsByName(name);
         return ResponseEntity.status(HttpStatus.OK).body(studentList);
     }

     @GetMapping("/getByLastname/{lastname}")
     public ResponseEntity<List<StudentResponseDTO>> getStudentByLastname(@PathVariable String lastname){
         List<StudentResponseDTO> studentList = studentService.getStudentsByLastname(lastname);
         return ResponseEntity.status(HttpStatus.OK).body(studentList);
     }

     @GetMapping("/getByFullName/name/{name}/lastname/{lastname}")
     public ResponseEntity<StudentResponseDTO> getByFullName(@PathVariable String name, @PathVariable String lastname){
         StudentResponseDTO student = studentService.getByFullName(name, lastname);
         return ResponseEntity.status(HttpStatus.OK).body(student);
     }

     @DeleteMapping("/getById/{id}")
     public ResponseEntity<Void> getStudentToDelete(@PathVariable Long id){
         studentService.deleteStudent(id);
         return ResponseEntity.noContent().build();
     }

     @PatchMapping("/update/{id}")
     public ResponseEntity<Void> getStudentToDelete(@PathVariable Long id, @RequestBody Student student){
         studentService.updateStudent(id, student);
         return ResponseEntity.noContent().build();
     }
}
