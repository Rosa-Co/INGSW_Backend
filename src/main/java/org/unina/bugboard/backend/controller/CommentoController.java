package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.model.Commento;
import org.unina.bugboard.backend.service.CommentoService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentoController {

    private final CommentoService commentoService;

    @Autowired
    public CommentoController(CommentoService commentoService) {
        this.commentoService = commentoService;
    }

    @GetMapping
    public List<Commento> getAllComments() {
        return commentoService.getAllComments();
    }

    @GetMapping("/issue/{issueId}")
    public List<Commento> getCommentsByIssue(@PathVariable Integer issueId) {
        return commentoService.getCommentsByIssueId(issueId);
    }

    @PostMapping
    public Commento createComment(@RequestBody Commento commento) {
        return commentoService.createComment(commento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentoService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
