package org.unina.bugboard.backend.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;
import org.unina.bugboard.backend.model.enums.Role;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

        @DataJpaTest
        class IssueRepositoryTest {

            @Autowired
            private IssueRepository issueRepository;

            @Autowired
            private UtenteRepository utenteRepository;

            private Utente u1;

            @BeforeEach
            void setUp() {
                // Pulisce il DB
                issueRepository.deleteAll();
                utenteRepository.deleteAll();

                u1 = new Utente();
                u1.setEmail("utente@example.com");
                u1.setPassword("password");
                u1.setRole(Role.USER);
                utenteRepository.save(u1);
                // Creazione di 4 issue di test
                Issue i1 = Issue.builder()
                        .titolo("Titolo1")
                        .creataDa(u1)
                        .tipologia(IssueType.QUESTION)
                        .stato(IssueStatus.TODO)
                        .priorita(IssuePriority.LOW)
                        .build();

                Issue i2 = Issue.builder()
                        .titolo("Titolo2")
                        .creataDa(u1)
                        .tipologia(IssueType.BUG)
                        .stato(IssueStatus.IN_PROGRESS)
                        .priorita(IssuePriority.MEDIUM)
                        .build();

                Issue i3 = Issue.builder()
                        .titolo("Titolo3")
                        .creataDa(u1)
                        .tipologia(IssueType.FEATURE)
                        .stato(IssueStatus.DONE)
                        .priorita(IssuePriority.HIGH)
                        .build();

                Issue i4 = Issue.builder()
                        .titolo("Titolo4")
                        .creataDa(u1)
                        .tipologia(IssueType.DOCUMENTATION)
                        .stato(IssueStatus.TODO)
                        .priorita(IssuePriority.HIGH)
                        .build();

                issueRepository.saveAll(List.of(i1, i2, i3, i4));
            }

            @Test
            @DisplayName("CE1: filtra question, todo, low")
            void testCE1() {
                List<Issue> issues = issueRepository.findIssuesWithFilters(
                        IssueType.QUESTION,
                        IssueStatus.TODO,
                        IssuePriority.LOW,
                        Sort.by(Sort.Direction.ASC, "tipologia")
                );

                assertEquals(1, issues.size());
                Issue i = issues.get(0);
                assertEquals(IssueType.QUESTION, i.getTipologia());
                assertEquals(IssueStatus.TODO, i.getStato());
                assertEquals(IssuePriority.LOW, i.getPriorita());
            }

            @Test
            @DisplayName("CE2: filtra bug, in progress, medium")
            void testCE2() {
                List<Issue> issues = issueRepository.findIssuesWithFilters(
                        IssueType.BUG,
                        IssueStatus.IN_PROGRESS,
                        IssuePriority.MEDIUM,
                        Sort.by(Sort.Direction.ASC, "tipologia")
                );

                assertEquals(1, issues.size());
                Issue i = issues.get(0);
                assertEquals(IssueType.BUG, i.getTipologia());
                assertEquals(IssueStatus.IN_PROGRESS, i.getStato());
                assertEquals(IssuePriority.MEDIUM, i.getPriorita());
            }

            @Test
            @DisplayName("CE3: filtra feature, done, high")
            void testCE3() {
                List<Issue> issues = issueRepository.findIssuesWithFilters(
                        IssueType.FEATURE,
                        IssueStatus.DONE,
                        IssuePriority.HIGH,
                        Sort.by(Sort.Direction.ASC, "tipologia")
                );

                assertEquals(1, issues.size());
                Issue i = issues.get(0);
                assertEquals(IssueType.FEATURE, i.getTipologia());
                assertEquals(IssueStatus.DONE, i.getStato());
                assertEquals(IssuePriority.HIGH, i.getPriorita());
            }

            @Test
            @DisplayName("CE4: filtra documentation, todo, high")
            void testCE4() {
                List<Issue> issues = issueRepository.findIssuesWithFilters(
                        IssueType.DOCUMENTATION,
                        IssueStatus.TODO,
                        IssuePriority.HIGH,
                        Sort.by(Sort.Direction.ASC, "tipologia")
                );

                assertEquals(1, issues.size());
                Issue i = issues.get(0);
                assertEquals(IssueType.DOCUMENTATION, i.getTipologia());
                assertEquals(IssueStatus.TODO, i.getStato());
                assertEquals(IssuePriority.HIGH, i.getPriorita());
            }

            @Test
            @DisplayName("CE5: filtri null restituiscono tutte le issue")
            void testCE5() {
                List<Issue> issues = issueRepository.findIssuesWithFilters(
                        null, null, null,
                        Sort.by(Sort.Direction.ASC, "tipologia")
                );

                assertEquals(4, issues.size());
            }
        }
