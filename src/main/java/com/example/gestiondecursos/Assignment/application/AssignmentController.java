package com.example.gestiondecursos.Assignment.application;

import com.example.gestiondecursos.Assignment.domain.Assignment;
import com.example.gestiondecursos.Assignment.domain.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/courseId/{id}")
    public ResponseEntity<Assignment> createAssignment(@PathVariable Long id, @RequestBody Assignment assignment){
        Assignment assignment1 = assignmentService.createAssignment(id, assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment1);
    }

    @PatchMapping("/assignmentId/{id}")
    public ResponseEntity<Void> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment){
        assignmentService.updateAssignment(id, assignment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAssignment/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id){
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<Assignment> getByTitle(@PathVariable String title){
        Assignment assignment = assignmentService.getByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(assignment);
    }
}
