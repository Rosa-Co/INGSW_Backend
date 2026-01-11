package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.unina.bugboard.backend.api.IssueApi;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.dto.IssueRequest;
import org.unina.bugboard.backend.mapper.IssueMapper;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueSortField;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;
import org.unina.bugboard.backend.security.UserDetailsImpl;
import org.unina.bugboard.backend.service.IssueService;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;

/**
 * Controller per la gestione delle issue (segnalazioni).
 * Fornisce endpoint per cercare, recuperare e creare issue.
 */
@RestController
// @CrossOrigin removed
public class IssueController implements IssueApi {

        private final IssueService issueService;
        private final UtenteService utenteService;
        private final IssueMapper issueMapper;

        /**
         * Costruttore per l'iniezione delle dipendenze.
         *
         * @param issueService  servizio per la logica di business delle issue
         * @param utenteService servizio per la gestione degli utenti
         * @param issueMapper   mapper per convertire tra entity e DTO
         */
        @Autowired
        public IssueController(IssueService issueService, UtenteService utenteService, IssueMapper issueMapper) {
                this.issueService = issueService;
                this.utenteService = utenteService;
                this.issueMapper = issueMapper;
        }

        /**
         * Recupera una lista di issue filtrate e ordinate.
         *
         * @param tipologia filtro per tipo di issue (opzionale)
         * @param stato     filtro per stato dell'issue (opzionale)
         * @param priorita  filtro per priorità dell'issue (opzionale)
         * @param sortBy    campo per l'ordinamento (es. "data", "titolo")
         * @param sortDir   direzione dell'ordinamento ("asc" o "desc")
         * @return una lista di IssueDTO che soddisfano i criteri di ricerca
         */
        @Override
        public List<IssueDTO> getAllIssues(IssueType tipologia, IssueStatus stato, IssuePriority priorita,
                                           IssueSortField sortBy,
                                           Sort.Direction sortDir) {
                return issueService.getAllIssues(tipologia, stato, priorita, sortBy, sortDir).stream()
                                .map(issueMapper::toDTO)
                                .toList();
        }

        /**
         * Recupera un'issue tramite il suo ID.
         *
         * @param id l'ID dell'issue da cercare
         * @return 200 OK con l'IssueDTO se trovata, altrimenti 404 Not Found
         */
        @Override
        public ResponseEntity<IssueDTO> getIssueById(Integer id) {
                return issueService.getIssueById(id)
                                .map(issueMapper::toDTO)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        /**
         * Crea una nuova issue.
         *
         * @param issueRequest richiesta contenente i dati della nuova issue
         * @return 200 OK con l'IssueDTO dell'issue appena creata
         * @throws RuntimeException se l'utente autenticato non viene trovato
         */
        @Override
        public ResponseEntity<IssueDTO> createIssue(IssueRequest issueRequest) {
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                                .getPrincipal();
                Utente currentUser = utenteService.getUserByEmail(userDetails.getEmail())
                                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

                Issue issue = Issue.builder()
                                .titolo(issueRequest.getTitolo())
                                .descrizione(issueRequest.getDescrizione())
                                .tipologia(issueRequest.getTipologia())
                                .img(issueRequest.getImg())
                                .priorita(issueRequest.getPriorita())
                                .stato(IssueStatus.TODO)
                                .creataDa(currentUser)
                                .build();

                Issue created = issueService.createIssue(issue);
                return ResponseEntity.ok(issueMapper.toDTO(created));
        }

}
