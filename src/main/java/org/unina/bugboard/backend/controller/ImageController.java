package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.unina.bugboard.backend.api.ImageApi;
import org.unina.bugboard.backend.service.IssueService;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController implements ImageApi {

    private final IssueService issueService;

    @Autowired
    public ImageController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Override
    public ResponseEntity<String> uploadImage(Integer issueId, MultipartFile file) throws IOException {
        String path = issueService.uploadImage(issueId, file);
        return ResponseEntity.ok(path);
    }

    @Override
    public ResponseEntity<Resource> getImage(String filename) throws IOException {
        Resource resource = issueService.loadImage(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
