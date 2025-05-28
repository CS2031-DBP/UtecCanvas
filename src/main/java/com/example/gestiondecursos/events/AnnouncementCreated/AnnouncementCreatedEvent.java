package com.example.gestiondecursos.events.AnnouncementCreated;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class AnnouncementCreatedEvent extends ApplicationEvent {
    private final List<String> recipients;
    private final String name;
    private final String lastname;
    private final Long courseId;
    private final String courseTitle; // <-- Nuevo campo
    private final String subject;

    public AnnouncementCreatedEvent(Object source, List<String> recipients, String name, String lastname, Long courseId, String courseTitle, String subject) {
        super(subject);
        this.recipients = recipients;
        this.name = name;
        this.lastname = lastname;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.subject = subject;
    }
}

