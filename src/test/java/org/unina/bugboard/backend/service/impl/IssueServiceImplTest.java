package org.unina.bugboard.backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.*;
import org.unina.bugboard.backend.repository.IssueRepository;
import org.unina.bugboard.backend.service.impl.IssueServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueServiceImplTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueServiceImpl service; // La tua classe che implementa getAllIssues

    private Issue issue1;
    private Issue issue2;
    private Issue issue3;
    private Issue issue4;
    private Issue issue5;

    @BeforeEach
    void setUp() {
        // Creo issues
        issue1 = new Issue();
        issue1.setTipologia(IssueType.BUG);
        issue1.setStato(IssueStatus.TODO);
        issue1.setPriorita(IssuePriority.HIGH);

        issue2 = new Issue();
        issue2.setTipologia(IssueType.FEATURE);
        issue2.setStato(IssueStatus.IN_PROGRESS);
        issue2.setPriorita(IssuePriority.LOW);


        issue3 = new Issue();
        issue3.setTipologia(IssueType.DOCUMENTATION);
        issue3.setStato(IssueStatus.TODO);
        issue3.setPriorita(IssuePriority.LOW);

        issue4 = new Issue();
        issue4.setTipologia(IssueType.QUESTION);
        issue4.setStato(IssueStatus.DONE);
        issue4.setPriorita(IssuePriority.MEDIUM);

        issue5 = new Issue();
        issue5.setTipologia(IssueType.BUG);
        issue5.setStato(IssueStatus.IN_PROGRESS);
        issue5.setPriorita(IssuePriority.MEDIUM);
    }

    @Test
    @DisplayName("getAllIssues: con filtri null restituisce tutte le issue")
    void getAllIssues_allFiltersNull_returnsAllIssues() {

        List<Issue> mockedResult = Arrays.asList(issue1, issue2, issue3, issue4,issue5);

        when(issueRepository.findIssuesWithFilters(
                any(), any(), any(), any(Sort.class)))
                .thenReturn(mockedResult);

        List<Issue> result = service.getAllIssues(
                null,
                null,
                null,
                IssueSortField.tipologia,
                Sort.Direction.ASC
        );

        assertNotNull(result);
        assertEquals(5, result.size());
        assertTrue(result.containsAll(mockedResult));
    }


    @Test
    @DisplayName("getAllIssues: filtro Question, TODO, LOW, ordinamento tipologia ASC")
    void getAllIssues_withQuestionTodoLowTipologiaAsc() {

        List<Issue> mockedResult = Collections.emptyList();

        when(issueRepository.findIssuesWithFilters(
                eq(IssueType.QUESTION),
                eq(IssueStatus.TODO),
                eq(IssuePriority.LOW),
                any(Sort.class)))
                .thenReturn(mockedResult);

        List<Issue> result = service.getAllIssues(
                IssueType.QUESTION,
                IssueStatus.TODO,
                IssuePriority.LOW,
                IssueSortField.tipologia,
                Sort.Direction.ASC
        );

        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista deve essere vuota perché nessuna issue corrisponde ai filtri");
    }

    @Test
    @DisplayName("getAllIssues: bug,in_progress,medium,stato,desc")
    void getAllIssues_withBugInProgressMediumStatoDesc() {

        List<Issue> mockedResult = Collections.singletonList(issue5);

        when(issueRepository.findIssuesWithFilters(
                eq(IssueType.BUG),
                eq(IssueStatus.IN_PROGRESS),
                eq(IssuePriority.MEDIUM),
                any(Sort.class)))
                .thenReturn(mockedResult);

        List<Issue> result = service.getAllIssues(
                IssueType.BUG,
                IssueStatus.IN_PROGRESS,
                IssuePriority.MEDIUM,
                IssueSortField.stato,
                Sort.Direction.DESC
        );

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("getAllIssues: feature,done,high,priorita,asc")
    void getAllIssues_withFeatureDoneHighPrioritaAsc() {

        List<Issue> mockedResult = Collections.emptyList();

        when(issueRepository.findIssuesWithFilters(
                eq(IssueType.FEATURE),
                eq(IssueStatus.DONE),
                eq(IssuePriority.HIGH),
                any(Sort.class)))
                .thenReturn(mockedResult);

        List<Issue> result = service.getAllIssues(
                IssueType.FEATURE,
                IssueStatus.DONE,
                IssuePriority.HIGH,
                IssueSortField.priorita,
                Sort.Direction.ASC
        );

        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista deve essere vuota perché nessuna issue corrisponde ai filtri");
    }

    @Test
    @DisplayName("getAllIssues: documentation,todo,low,tipologia,desc")
    void getAllIssues_withDocumentationTodoLowTipologiaDesc() {
        List<Issue> mockedResult = Arrays.asList(issue3, issue3);

        when(issueRepository.findIssuesWithFilters(
                eq(IssueType.DOCUMENTATION),
                eq(IssueStatus.TODO),
                eq(IssuePriority.LOW),
                any(Sort.class)))
                .thenReturn(mockedResult);

        List<Issue> result = service.getAllIssues(
                IssueType.DOCUMENTATION,
                IssueStatus.TODO,
                IssuePriority.LOW,
                IssueSortField.tipologia,
                Sort.Direction.DESC
        );

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getAllIssues: sortBy null lancia eccezione")
    void getAllIssues_sortByNull_throwsException() {

        assertThrows(NullPointerException.class, () -> {
            service.getAllIssues(
                    null,
                    null,
                    null,
                    null,
                    Sort.Direction.ASC
            );
        });
    }

    @Test
    @DisplayName("getAllIssues: sortDir null lancia eccezione")
    void getAllIssues_sortDirNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getAllIssues(
                    null,
                    null,
                    null,
                    IssueSortField.tipologia,
                    null
            );
        });
    }

    // ! TUTTE LE CE6 NON SONO TESTABILI NEL CASO PRATICO, PERCHE' STIAMO LAVORANDO CON ENUMS E NON POSSONO ESSERCI VALORI DIVERSI DA QUELLI DEFINITI NELL'ENUM


}