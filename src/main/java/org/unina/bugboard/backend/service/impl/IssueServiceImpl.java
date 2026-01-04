package org.unina.bugboard.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;
import org.unina.bugboard.backend.repository.IssueRepository;
import org.unina.bugboard.backend.service.IssueService;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public Optional<Issue> getIssueById(Integer id) {
        return issueRepository.findById(id);
    }

    @Override
    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getIssuesByStatus(String status) {
        return issueRepository.findByStato(status);
    }

    @Override
    public List<Issue> getAllIssues(IssueType tipologia, IssueStatus stato, IssuePriority priorita, String sortBy,
            String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        return issueRepository.findIssuesWithFilters(tipologia, stato, priorita, sort);
    }
}
