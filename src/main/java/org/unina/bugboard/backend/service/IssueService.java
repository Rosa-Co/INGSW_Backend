package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    List<Issue> getAllIssues();

    Optional<Issue> getIssueById(Integer id);

    Issue createIssue(Issue issue);

    List<Issue> getIssuesByStatus(String status);

    List<Issue> getAllIssues(IssueType tipologia, IssueStatus stato, IssuePriority priorita, String sortBy,
            String sortDir);
}
