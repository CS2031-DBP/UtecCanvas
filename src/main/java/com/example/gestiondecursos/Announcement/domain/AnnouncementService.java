package com.example.gestiondecursos.Announcement.domain;

import com.example.gestiondecursos.Announcement.dto.AnnouncementResponseDTO;
import com.example.gestiondecursos.Announcement.infrastructure.AnnouncementRepository;
import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.domain.CourseService;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Instructor.domain.InstructorService;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.domain.UserService;
import com.example.gestiondecursos.events.AnnouncementCreated.AnnouncementCreatedEvent;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final UserService userService;
    private final InstructorService instructorService;
    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final AnnouncementRepository announcementRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AnnouncementResponseDTO createAnnouncement(Announcement announcementRequest, Long courseId) {
        Instructor instructor = instructorService.getMe();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFound("Course not found"));

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementRequest.getTitle());
        announcement.setMessage(announcementRequest.getMessage());
        announcement.setInstructorName(instructor.getName());
        announcement.setInstructorLastname(instructor.getLastname());
        announcement.setCourse(course);
        announcement.setCreatedAt(LocalDateTime.now());

        Announcement saved = announcementRepository.save(announcement);

        List<String> studentEmails = getAllEmailsByCourse(course);

//        applicationEventPublisher.publishEvent(
//                new AnnouncementCreatedEvent(
//                        this,
//                        studentEmails,
//                        instructor.getName(),
//                        instructor.getLastname(),
//                        courseId,
//                        course.getTitle(),
//                        announcement.getMessage()
//                )
//        );


        return modelMapper.map(saved, AnnouncementResponseDTO.class);
    }

    private List<String> getAllEmailsByCourse(Course course) {
        return course.getEnrollmentList().stream()
                .map(e -> e.getStudent().getEmail())
                .distinct()
                .toList();
    }
}
