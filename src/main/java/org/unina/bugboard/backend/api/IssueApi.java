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

@RequestMapping("/api/issues")
public interface IssueApi {

    @GetMapping
    List<IssueDTO> getAllIssues(@RequestParam(required = false) IssueType tipologia,
            @RequestParam(required = false) IssueStatus stato,
            @RequestParam(required = false) IssuePriority priorita,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir);

    @GetMapping("/{id}")
    ResponseEntity<IssueDTO> getIssueById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueRequest issueRequest);

}
