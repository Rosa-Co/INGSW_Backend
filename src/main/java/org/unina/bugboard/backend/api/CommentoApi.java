package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.CommentoDTO;
import org.unina.bugboard.backend.dto.CommentoRequest;

import java.util.List;

/**
 * Interfaccia API per la gestione dei commenti.
 */
@RequestMapping("/api/comments")
public interface CommentoApi {

    /**
     * Recupera tutti i commenti.
     *
     * @return lista di tutti i commenti
     */
    @GetMapping
    List<CommentoDTO> getAllComments();

    /**
     * Recupera i commenti per una specifica issue.
     *
     * @param issueId l'ID dell'issue
     * @return lista dei commenti associati all'issue
     */
    @GetMapping("/issue/{issueId}")
    List<CommentoDTO> getCommentsByIssue(@PathVariable Integer issueId);

    /**
     * Crea un nuovo commento.
     *
     * @param commentoRequest la richiesta di creazione del commento
     * @return il commento creato
     */
    @PostMapping
    ResponseEntity<CommentoDTO> createComment(@Valid @RequestBody CommentoRequest commentoRequest);

}
