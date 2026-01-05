package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.dto.IssueRequest;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

import java.util.List;

/**
 * Interfaccia API per la gestione delle issue.
 */
@RequestMapping("/api/issues")
public interface IssueApi {

        /**
         * Recupera una lista di issue con filtri opzionali.
         *
         * @param tipologia filtro per tipo
         * @param stato     filtro per stato
         * @param priorita  filtro per priorità
         * @param sortBy    campo per l'ordinamento
         * @param sortDir   direzione dell'ordinamento
         * @return lista filtrata di issue
         */
        @GetMapping
        List<IssueDTO> getAllIssues(@RequestParam(required = false) IssueType tipologia,
                        @RequestParam(required = false) IssueStatus stato,
                        @RequestParam(required = false) IssuePriority priorita,
                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                        @RequestParam(required = false, defaultValue = "ASC") String sortDir);

        /**
         * Recupera una issue per ID.
         *
         * @param id l'ID dell'issue
         * @return l'issue trovata
         */
        @GetMapping("/{id}")
        ResponseEntity<IssueDTO> getIssueById(@PathVariable Integer id);

        /**
         * Crea una nuova issue.
         *
         * @param issueRequest la richiesta di creazione issue
         * @return l'issue creata
         */
        @PostMapping
        ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueRequest issueRequest);

}
