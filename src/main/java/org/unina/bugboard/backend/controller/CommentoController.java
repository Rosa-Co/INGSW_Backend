package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

/**
 * Controller per la gestione dei commenti.
 * Fornisce endpoint per creare e recuperare commenti.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentoController implements CommentoApi {

        private final CommentoService commentoService;
        private final UtenteService utenteService;
        private final IssueService issueService;
        private final CommentoMapper commentoMapper;

        /**
         * Costruttore per l'iniezione delle dipendenze.
         *
         * @param commentoService servizio per la logica di business dei commenti
         * @param utenteService   servizio per la gestione degli utenti
         * @param issueService    servizio per la gestione delle issue
         * @param commentoMapper  mapper per convertire tra entity e DTO
         */
        @Autowired
        public CommentoController(CommentoService commentoService, UtenteService utenteService,
                        IssueService issueService,
                        CommentoMapper commentoMapper) {
                this.commentoService = commentoService;
                this.utenteService = utenteService;
                this.issueService = issueService;
                this.commentoMapper = commentoMapper;
        }

        /**
         * Recupera tutti i commenti presenti nel sistema.
         *
         * @return una lista di CommentoDTO
         */
        @Override
        public List<CommentoDTO> getAllComments() {
                return commentoService.getAllComments().stream()
                                .map(commentoMapper::toDTO)
                                .toList();
        }

        /**
         * Recupera tutti i commenti associati a una specifica issue.
         *
         * @param issueId l'ID dell'issue di cui recuperare i commenti
         * @return una lista di CommentoDTO appartenenti all'issue specificata
         */
        @Override
        public List<CommentoDTO> getCommentsByIssue(Integer issueId) {
                return commentoService.getCommentsByIssueId(issueId).stream()
                                .map(commentoMapper::toDTO)
                                .toList();
        }

        /**
         * Crea un nuovo commento per una issue.
         *
         * @param commentoRequest richiesta contenente il testo del commento e l'ID
         *                        dell'issue
         * @return 200 OK con il CommentoDTO appena creato
         * @throws RuntimeException se l'utente autenticato o l'issue non vengono
         *                          trovati
         */
        @Override
        public ResponseEntity<CommentoDTO> createComment(CommentoRequest commentoRequest) {
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
