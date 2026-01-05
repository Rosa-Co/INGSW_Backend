package org.unina.bugboard.backend.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

@RequestMapping("/api/images")
public interface ImageApi {

    @PostMapping(value = "/upload/{issueId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImage(@PathVariable Integer issueId, @RequestParam("file") MultipartFile file)
            throws IOException;

    @GetMapping("/{filename:.+}")
    ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException;
}
