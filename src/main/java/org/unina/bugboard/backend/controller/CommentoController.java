package org.unina.bugboard.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.api.CommentoApi;
import org.unina.bugboard.backend.dto.CommentoDTO;
import org.unina.bugboard.backend.dto.CommentoRequest;
import org.unina.bugboard.backend.mapper.CommentoMapper;
import org.unina.bugboard.backend.model.Commento;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.security.UserDetailsImpl;
import org.unina.bugboard.backend.service.CommentoService;
import org.unina.bugboard.backend.service.IssueService;
import org.unina.bugboard.backend.service.UtenteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class CommentoController implements CommentoApi {

    private final CommentoService commentoService;
    private final UtenteService utenteService;
    private final IssueService issueService;
    private final CommentoMapper commentoMapper;

    @Autowired
    public CommentoController(CommentoService commentoService, UtenteService utenteService, IssueService issueService,
            CommentoMapper commentoMapper) {
        this.commentoService = commentoService;
        this.utenteService = utenteService;
        this.issueService = issueService;
        this.commentoMapper = commentoMapper;
    }

    @Override
    public List<CommentoDTO> getAllComments() {
        return commentoService.getAllComments().stream()
                .map(commentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentoDTO> getCommentsByIssue(Integer issueId) {
        return commentoService.getCommentsByIssueId(issueId).stream()
                .map(commentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CommentoDTO> createComment(@Valid @RequestBody CommentoRequest commentoRequest) {
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
        return ResponseEntity.ok(commentoMapper.toDTO(created));
    }

}
