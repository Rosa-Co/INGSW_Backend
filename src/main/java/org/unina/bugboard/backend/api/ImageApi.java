package org.unina.bugboard.backend.api;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/images")
public interface ImageApi {

    @PostMapping(value = "/upload/{issueId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImage(@PathVariable Integer issueId, @RequestParam("file") MultipartFile file)
            throws IOException;

    @GetMapping("/{filename:.+}")
    ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException;
}
