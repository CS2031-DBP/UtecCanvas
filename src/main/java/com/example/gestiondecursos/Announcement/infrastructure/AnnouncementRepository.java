package com.example.gestiondecursos.Announcement.infrastructure;

import com.example.gestiondecursos.Announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
