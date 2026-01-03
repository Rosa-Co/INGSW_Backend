package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.dto.IssueRequest;

import java.util.List;

@RequestMapping("/api/issues")
public interface IssueApi {

    @GetMapping
    List<IssueDTO> getAllIssues();

    @GetMapping("/{id}")
    ResponseEntity<IssueDTO> getIssueById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueRequest issueRequest);

}
