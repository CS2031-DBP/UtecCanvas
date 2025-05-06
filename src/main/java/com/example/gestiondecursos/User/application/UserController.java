package com.example.gestiondecursos.User.application;

import com.example.gestiondecursos.User.domain.UserService;
import com.example.gestiondecursos.User.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(){
        UserResponseDTO currentUser = userService.getMe();
        return ResponseEntity.ok(currentUser);
    }
}
