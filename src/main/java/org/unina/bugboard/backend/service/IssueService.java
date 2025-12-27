package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Issue;
import java.util.List;
import java.util.Optional;

public interface IssueService {
    List<Issue> getAllIssues();

    Optional<Issue> getIssueById(Integer id);

    Issue createIssue(Issue issue);

    Issue updateIssue(Integer id, Issue issueDetails);

    void deleteIssue(Integer id);

    List<Issue> getIssuesByStatus(String status);
}
