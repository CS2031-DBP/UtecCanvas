package com.example.gestiondecursos.Announcement.domain;

import com.example.gestiondecursos.Announcement.dto.AnnouncementResponseDTO;
import com.example.gestiondecursos.Announcement.infrastructure.AnnouncementRepository;
import com.example.gestiondecursos.Course.domain.CourseService;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.domain.InstructorService;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final UserService userService;
    private final InstructorService instructorService;
    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final AnnouncementRepository announcementRepository;


//    public AnnouncementResponseDTO createAnnouncement(Announcement announcement1, String courseTitle){
//        Instructor instructor = instructorService.getMe();
//        for(int i = 0; i < instructor.getCourseList().size(); i++){
//            if(instructor.getCourseList().contains(courseTitle)){
//
//            }
//        }
//        Announcement announcement = new Announcement();
//        announcement.setTitle(announcement1.getTitle());
//        announcement.setMessage(announcement1.getMessage());
//        announcement.setInstructorName(instructor.getUsername());
//        announcement.setInstructorLastname(instructor.getLastname());
//        announcement.setCreatedAt(LocalDateTime.now());
//        return modelMapper.map(announcement, AnnouncementResponseDTO.class);
//    }
}
