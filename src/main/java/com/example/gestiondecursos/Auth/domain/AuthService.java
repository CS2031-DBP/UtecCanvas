package com.example.gestiondecursos.Auth.domain;

import com.example.gestiondecursos.Auth.dto.JwtAuthResponse;
import com.example.gestiondecursos.Auth.dto.LoginDTO;
import com.example.gestiondecursos.Auth.dto.RegisterDTO;
import com.example.gestiondecursos.Config.JwtService;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.domain.InstructorService;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.infrastructure.BaseUserRepository;
import com.example.gestiondecursos.exceptions.PasswordIncorrectException;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import com.example.gestiondecursos.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.gestiondecursos.User.domain.Roles.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BaseUserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final InstructorService instructorService;

    public JwtAuthResponse login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new ResourceNotFound("User doesn't exist or incorrect email "));
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new PasswordIncorrectException("Your password is incorrect");
        }
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generatedToken(user));
        return response;
    }

    public JwtAuthResponse register(RegisterDTO registerDTO){
        User user = userRepository.findByEmail(registerDTO.getEmail()).orElseThrow(() -> new UserAlreadyExistException("Email already register"));

        if(registerDTO.getRole() == "INSTRUCTOR"){
            Instructor instructor = new Instructor();
            instructor.setName(registerDTO.getName());
            instructor.setLastname(registerDTO.getLastname());
            instructor.setEmail(registerDTO.getEmail());
            instructor.setPassword(registerDTO.getPassword());
            instructor.setRole(INSTRUCTOR);
            userRepository.save(instructor);
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generatedToken(instructor));
            return response;
        }
        else if (registerDTO.getRole() == "STUDENT") {
            Student student = new Student();
            student.setName(registerDTO.getName());
            student.setLastname(registerDTO.getLastname());
            student.setEmail(registerDTO.getEmail());
            student.setPassword(registerDTO.getPassword());
            student.setRole(STUDENT);
            userRepository.save(student);
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generatedToken(student));
            return response;
        }
        else{
            User user1 = new User();
            user1.setName(registerDTO.getName());
            user1.setLastname(registerDTO.getLastname());
            user1.setEmail(registerDTO.getEmail());
            user1.setPassword(registerDTO.getPassword());
            user1.setRole(ADMIN);
            userRepository.save(user1);
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generatedToken(user1));
            return response;
        }


    }
}
