package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.unina.bugboard.backend.api.IssueApi;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.dto.IssueRequest;
import org.unina.bugboard.backend.mapper.IssueMapper;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.security.UserDetailsImpl;
import org.unina.bugboard.backend.service.IssueService;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;

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
                .toList();
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(Integer id) {
        return issueService.getIssueById(id)
                .map(issueMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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
