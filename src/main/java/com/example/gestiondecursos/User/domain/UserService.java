package com.example.gestiondecursos.User.domain;

import com.example.gestiondecursos.Instructor.infrastructure.InstructorRepository;
import com.example.gestiondecursos.Student.infrastructure.StudentRepository;
import com.example.gestiondecursos.User.infrastructure.BaseUserRepository;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BaseUserRepository<User> userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    public User getByEmail(String username, String role){
        User user;
        if(role.equals("ROLE_STUDENT")){
            user = studentRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFound("Student not found"));
        }else{
            user = instructorRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFound("Instructor not found"));
        }
        return user;
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFound("User not found"));
            return (UserDetails) user;
        };
    }
}
