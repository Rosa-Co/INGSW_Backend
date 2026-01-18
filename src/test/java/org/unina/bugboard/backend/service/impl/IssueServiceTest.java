package org.unina.bugboard.backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueSortField;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;
import org.unina.bugboard.backend.repository.IssueRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("IssueService - getAllIssues Unit Tests")
class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueServiceImpl issueService;

    private List<Issue> mockIssues;

    @BeforeEach
    void setUp() {
        Issue issue1 = new Issue();
        issue1.setId(1);

        Issue issue2 = new Issue();
        issue2.setId(2);

        mockIssues = Arrays.asList(issue1, issue2);
    }

    @Test
    @DisplayName("[TC-S01] All Parameters are valued -> Successfully calls Repository")
    void whenAllParametersAreValued_thenCallsRepositoryWithCorrectParameters() {
        IssueType tipologia = IssueType.QUESTION;
        IssueStatus stato = IssueStatus.TODO;
        IssuePriority priorita = IssuePriority.LOW;
        IssueSortField sortBy = IssueSortField.TIPOLOGIA;
        Sort.Direction sortDir = Sort.Direction.ASC;
        Sort expectedSort = Sort.by(sortDir, sortBy.getFieldName());

        when(issueRepository.findIssuesWithFilters(eq(tipologia), eq(stato), eq(priorita), any()))
                .thenReturn(mockIssues);

        List<Issue> result = issueService.getAllIssues(tipologia, stato, priorita, sortBy, sortDir);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(mockIssues);
        verify(issueRepository, times(1)).findIssuesWithFilters(tipologia, stato, priorita, expectedSort);
    }

    @Test
    @DisplayName("[TC-S02] Only sortBy and sortDir are valued -> Successfully calls Repository")
    void whenOnlySortByAndSortDirAreValued_thenCallsRepositoryWithCorrectParameters() {
        IssueType tipologia = null;
        IssueStatus stato = null;
        IssuePriority priorita = null;
        IssueSortField sortBy = IssueSortField.STATO;
        Sort.Direction sortDir = Sort.Direction.DESC;
        Sort expectedSort = Sort.by(sortDir, sortBy.getFieldName());

        when(issueRepository.findIssuesWithFilters(eq(null), eq(null), eq(null), any()))
                .thenReturn(mockIssues);

        List<Issue> result = issueService.getAllIssues(tipologia, stato, priorita, sortBy, sortDir);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(mockIssues);
        verify(issueRepository, times(1)).findIssuesWithFilters(tipologia, stato, priorita, expectedSort);
    }

    @Test
    @DisplayName("[TC-S03] sortDir is null -> Sort Throws Exception ")
    void whenSortDirIsNull_thenSortThrowsException() {
        IssueType tipologia = IssueType.FEATURE;
        IssueStatus stato = IssueStatus.DONE;
        IssuePriority priorita = IssuePriority.HIGH;
        IssueSortField sortBy = IssueSortField.PRIORITA;
        Sort.Direction sortDir = null;

        assertThrows(IllegalArgumentException.class, () -> {
            issueService.getAllIssues(tipologia, stato, priorita, sortBy, sortDir);
        });
        verify(issueRepository, never()).findIssuesWithFilters(any(), any(), any(), any());
    }

    @Test
    @DisplayName("[TC-S04] sortBy is null -> Sort Throws Exception ")
    void whenSortByIsNull_thenSortThrowsException() {

        IssueType tipologia = IssueType.DOCUMENTATION;
        IssueStatus stato = null;
        IssuePriority priorita = null;
        IssueSortField sortBy = null;
        Sort.Direction sortDir = Sort.Direction.DESC;

        assertThrows(NullPointerException.class, () -> {
            issueService.getAllIssues(tipologia, stato, priorita, sortBy, sortDir);
        });
        verify(issueRepository, never()).findIssuesWithFilters(any(), any(), any(), any());
    }
}
