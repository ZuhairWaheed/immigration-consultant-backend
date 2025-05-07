package com.immigrationConsultant.backend.controller;

import com.immigrationConsultant.backend.model.Document;
import com.immigrationConsultant.backend.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("type") String type,
                                         Principal principal) {
        try {
            documentService.saveDocument(file, type, principal.getName());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @GetMapping("/my-documents")
    public ResponseEntity<List<Document>> myDocuments(Principal principal) {
        return ResponseEntity.ok(documentService.getDocumentsByUser(principal.getName()));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Document>> allDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

}
