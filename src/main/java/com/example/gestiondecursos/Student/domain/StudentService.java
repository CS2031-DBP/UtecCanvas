package com.example.gestiondecursos.Student.domain;

import com.example.gestiondecursos.Auth.utils.AuthorizationUtils;
import com.example.gestiondecursos.Student.Dto.StudentResponseDTO;
import com.example.gestiondecursos.Student.infrastructure.StudentRepository;
import com.example.gestiondecursos.User.domain.Roles;
import com.example.gestiondecursos.exceptions.ResourceIsNullException;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public StudentResponseDTO getStudentInfo(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Student not found"));
        StudentResponseDTO studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        return studentResponseDTO;
    }

    public StudentResponseDTO getStudentOwnInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isStudent = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        if (!isStudent) {
            throw new AccessDeniedException("Only students can access this resource");
        }
        String username = authorizationUtils.getCurrentUser();
        if (username == null) {
            throw new ResourceNotFound("User not found");
        }
        Student student = studentRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFound("Student not found"));
        return getStudentInfo(student.getId());
    }


    public StudentResponseDTO createStudent(Student student){
        Student student1 = new Student();
        student1.setName(student.getName());
        student1.setLastname(student.getLastname());
        student1.setEmail(student.getEmail());
        student1.setPassword(student.getPassword());
        student1.setDescription(student.getDescription());
        student1.setProfilePhoto(student.getProfilePhoto());
        student1.setRole(Roles.STUDENT);
        studentRepository.save(student1);
        StudentResponseDTO studentResponseDTO = modelMapper.map(student1, StudentResponseDTO.class);
        return studentResponseDTO;
    }

    public StudentResponseDTO getStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Student not found"));
        StudentResponseDTO studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        return studentResponseDTO;
    }

    public List<StudentResponseDTO> getStudentsByName(String name){
        List<Student> studentList = studentRepository.findByName(name);
        List<StudentResponseDTO> studentResponseDTOS = studentList.stream().map(student -> modelMapper.map(student, StudentResponseDTO.class)).collect(Collectors.toList());
        return studentResponseDTOS;
    }

    public List<StudentResponseDTO> getStudentsByLastname(String lastname){
        List<Student> studentList = studentRepository.findByLastname(lastname);
        List<StudentResponseDTO> studentResponseDTOS = studentList.stream().map(student -> modelMapper.map(student, StudentResponseDTO.class)).collect(Collectors.toList());
        return studentResponseDTOS;
    }

    public StudentResponseDTO getByFullName(String name, String lastname){
        Student student = studentRepository.findByNameAndLastname(name, lastname).orElseThrow(() -> new ResourceNotFound("Student not found"));
        StudentResponseDTO studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        return studentResponseDTO;
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
