package org.unina.bugboard.backend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.CommentoDTO;
import org.unina.bugboard.backend.dto.CommentoRequest;

import jakarta.validation.Valid;
import java.util.List;

@RequestMapping("/api/comments")
public interface CommentoApi {

    @GetMapping
    List<CommentoDTO> getAllComments();

    @GetMapping("/issue/{issueId}")
    List<CommentoDTO> getCommentsByIssue(@PathVariable Integer issueId);

    @PostMapping
    ResponseEntity<CommentoDTO> createComment(@Valid @RequestBody CommentoRequest commentoRequest);

}
