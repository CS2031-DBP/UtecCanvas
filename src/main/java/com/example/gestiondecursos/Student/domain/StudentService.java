package com.example.gestiondecursos.Student.domain;

import com.example.gestiondecursos.Student.infrastructure.StudentRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student getStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Student not found"));
        return student;
    }

    public List<Student> getStudentsByName(String name){
        List<Student> studentList = studentRepository.findByName(name);
        return  studentList;
    }

    public List<Student> getStudentsByLastname(String lastname){
        List<Student> studentList = studentRepository.findByLastname(lastname);
        return  studentList;
    }

    public Student getByFullName(String name, String lastname){
        Student student = studentRepository.findByNameAndLastname(name, lastname).orElseThrow(() -> new ResourceNotFound("Student not found"));
        return student;
    }

    public void deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Student not found"));
        studentRepository.delete(student);
    }

    public void updateStudent(Long id, Student student){
        Student updatedStudent = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Student not found"));
        if(student.getDescription() != null){
            updatedStudent.setDescription(student.getDescription());
        }
        if(student.getProfilePhoto() != null){
            updatedStudent.setProfilePhoto(student.getProfilePhoto());
        }
        studentRepository.save(updatedStudent);
    }
}
