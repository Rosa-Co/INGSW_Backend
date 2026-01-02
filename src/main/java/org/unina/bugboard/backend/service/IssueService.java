package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Issue;
import java.util.List;
import java.util.Optional;

public interface IssueService {
    List<Issue> getAllIssues();

    Optional<Issue> getIssueById(Integer id);

    Issue createIssue(Issue issue);

    List<Issue> getIssuesByStatus(String status);
}
