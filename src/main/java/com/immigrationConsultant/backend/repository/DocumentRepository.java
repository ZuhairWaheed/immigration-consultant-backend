package com.immigrationConsultant.backend.repository;

import com.immigrationConsultant.backend.model.Document;
import com.immigrationConsultant.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);
}
