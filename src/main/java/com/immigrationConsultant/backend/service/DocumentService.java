package com.immigrationConsultant.backend.service;

import com.immigrationConsultant.backend.model.Document;
import com.immigrationConsultant.backend.model.User;
import com.immigrationConsultant.backend.repository.DocumentRepository;
import com.immigrationConsultant.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public void saveDocument(MultipartFile file, String documentType, String email) throws IOException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String fileName = file.getOriginalFilename();
        String userDir = uploadDir + "/" + user.getId();
        Files.createDirectories(Paths.get(userDir));

        Path filePath = Paths.get(userDir, fileName);
        Files.write(filePath, file.getBytes());

        Document doc = new Document();
        doc.setDocumentName(fileName);
        doc.setDocumentType(documentType);
        doc.setFilePath(filePath.toString());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setUser(user);

        documentRepository.save(doc);
    }

    public List<Document> getDocumentsByUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return documentRepository.findByUser(user);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

}
