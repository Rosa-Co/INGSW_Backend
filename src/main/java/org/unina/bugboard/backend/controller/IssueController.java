package org.unina.bugboard.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.api.IssueApi;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.dto.IssueRequest;
import org.unina.bugboard.backend.mapper.IssueMapper;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.security.UserDetailsImpl;
import org.unina.bugboard.backend.service.IssueService;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class IssueController implements IssueApi {

    private final IssueService issueService;
    private final UtenteService utenteService;
    private final IssueMapper issueMapper;

    @Autowired
    public IssueController(IssueService issueService, UtenteService utenteService, IssueMapper issueMapper) {
        this.issueService = issueService;
        this.utenteService = utenteService;
        this.issueMapper = issueMapper;
    }

    @Override
    public List<IssueDTO> getAllIssues() {
        return issueService.getAllIssues().stream()
                .map(issueMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable Integer id) {
        return issueService.getIssueById(id)
                .map(issueMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueRequest issueRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Utente currentUser = utenteService.getUserByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        Issue issue = new Issue();
        issue.setTitolo(issueRequest.getTitolo());
        issue.setDescrizione(issueRequest.getDescrizione());
        issue.setTipologia(issueRequest.getTipologia());
        issue.setImg(issueRequest.getImg());
        issue.setPriorita(issueRequest.getPriorita());
        issue.setStato("OPEN"); // Default status
        issue.setCreataDa(currentUser);

        Issue created = issueService.createIssue(issue);
        return ResponseEntity.ok(issueMapper.toDTO(created));
    }

    @Override
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable Integer id,
            @Valid @RequestBody IssueRequest issueDetails) {
        try {
            Issue issueUpdates = new Issue();
            // We only map fields that are present in the request.
            if (issueDetails.getTitolo() != null)
                issueUpdates.setTitolo(issueDetails.getTitolo());
            if (issueDetails.getDescrizione() != null)
                issueUpdates.setDescrizione(issueDetails.getDescrizione());
            if (issueDetails.getTipologia() != null)
                issueUpdates.setTipologia(issueDetails.getTipologia());
            if (issueDetails.getPriorita() != null)
                issueUpdates.setPriorita(issueDetails.getPriorita());
            if (issueDetails.getStato() != null)
                issueUpdates.setStato(issueDetails.getStato());
            if (issueDetails.getImg() != null)
                issueUpdates.setImg(issueDetails.getImg());

            Issue updated = issueService.updateIssue(id, issueUpdates);
            return ResponseEntity.ok(issueMapper.toDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteIssue(@PathVariable Integer id) {
        issueService.deleteIssue(id);
        return ResponseEntity.ok().build();
    }
}
