package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.CommentoDTO;
import org.unina.bugboard.backend.dto.CommentoRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.model.Commento;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.service.CommentoService;
import org.unina.bugboard.backend.service.IssueService;
import org.unina.bugboard.backend.service.UtenteService;
import org.unina.bugboard.backend.service.impl.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentoController {

    private final CommentoService commentoService;
    private final UtenteService utenteService;
    private final IssueService issueService;

    @Autowired
    public CommentoController(CommentoService commentoService, UtenteService utenteService, IssueService issueService) {
        this.commentoService = commentoService;
        this.utenteService = utenteService;
        this.issueService = issueService;
    }

    private CommentoDTO mapToDTO(Commento commento) {
        UtenteDTO userDto = new UtenteDTO(
                commento.getScrittoDa().getId(),
                commento.getScrittoDa().getEmail(),
                commento.getScrittoDa().getIsAdmin());
        return new CommentoDTO(
                commento.getId(),
                commento.getDescrizione(),
                commento.getData(),
                userDto,
                commento.getAppartiene().getId());
    }

    @GetMapping
    public List<CommentoDTO> getAllComments() {
        return commentoService.getAllComments().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/issue/{issueId}")
    public List<CommentoDTO> getCommentsByIssue(@PathVariable Integer issueId) {
        return commentoService.getCommentsByIssueId(issueId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CommentoDTO> createComment(@RequestBody CommentoRequest commentoRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Utente currentUser = utenteService.getUserByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        Issue issue = issueService.getIssueById(commentoRequest.getAppartieneId())
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        Commento commento = new Commento();
        commento.setDescrizione(commentoRequest.getDescrizione());
        commento.setData(LocalDateTime.now());
        commento.setScrittoDa(currentUser);
        commento.setAppartiene(issue);

        Commento created = commentoService.createComment(commento);
        return ResponseEntity.ok(mapToDTO(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        // Ideally enforce that only author or admin can delete
        commentoService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
