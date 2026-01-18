package org.unina.bugboard.backend.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.mapper.IssueMapper;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueSortField;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;
import org.unina.bugboard.backend.service.IssueService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("IssueController - getAllIssues Unit Tests")
class IssueControllerTest {

        @Mock
        private IssueService issueService;

        @Mock
        private IssueMapper issueMapper;

        @InjectMocks
        private IssueController issueController;

        private Issue mockIssue1;
        private Issue mockIssue2;
        private IssueDTO mockDTO1;
        private IssueDTO mockDTO2;

        @BeforeEach
        void setUp() {
                mockIssue1 = new Issue();
                mockIssue2 = new Issue();

                mockDTO1 = new IssueDTO();
                mockDTO1.setId(1);
                mockDTO1.setTipologia(IssueType.BUG);

                mockDTO2 = new IssueDTO();
                mockDTO2.setId(2);
                mockDTO2.setTipologia(IssueType.FEATURE);
        }

        @Nested
        @DisplayName("Test CE - Parametro Tipologia (rappresentativo)")
        class TipologiaTests {

                @Test
                @DisplayName("[TC-01] CE: tipologia = QUESTION (valore rappresentativo)")
                void whenTipologiaIsQuestion_thenDelegatesCorrectly() {
                        when(issueService.getAllIssues(eq(IssueType.QUESTION), any(), any(), any(), any()))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(IssueType.QUESTION, null, null, null,
                                        null);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(IssueType.QUESTION, null, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

        }

        @Nested
        @DisplayName("Test CE - Parametro Stato (rappresentativo)")
        class StatoTests {

                @Test
                @DisplayName("[TC-02] CE: stato = TODO")
                void whenStatoIsTodo_thenDelegatesCorrectly() {
                        when(issueService.getAllIssues(any(), eq(IssueStatus.TODO), any(), any(), any()))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, IssueStatus.TODO, null, null, null);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(null, IssueStatus.TODO, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

        }

        @Nested
        @DisplayName("Test CE - Parametro Priorità (rappresentativo)")
        class PrioritaTests {

                @Test
                @DisplayName("[TC-03] CE: priorita = LOW")
                void whenPrioritaIsLow_thenDelegatesCorrectly() {
                        when(issueService.getAllIssues(any(), any(), eq(IssuePriority.LOW), any(), any()))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, IssuePriority.LOW, null, null);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(null, null, IssuePriority.LOW, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

        }

        @Nested
        @DisplayName("Test CE - Parametro SortBy / SortDir ")
        class SortTests {

                @Test
                @DisplayName("[TC-04] CE: sortBy = TIPOLOGIA")
                void whenSortByIsTipologia_thenUsesIt() {
                        when(issueService.getAllIssues(any(), any(), any(), eq(IssueSortField.TIPOLOGIA), any()))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, IssueSortField.TIPOLOGIA,
                                        null);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(null, null, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

                @Test
                @DisplayName("[TC-05] CE: sortDir = ASC")
                void whenSortDirIsAsc_thenUsesIt() {
                        when(issueService.getAllIssues(any(), any(), any(), any(), eq(Sort.Direction.ASC)))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null,
                                        Sort.Direction.ASC);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(null, null, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

                @Test
                @DisplayName("[TC-06] CE: sortDir = DESC")
                void whenSortDirIsDesc_thenUsesIt() {
                        when(issueService.getAllIssues(any(), any(), any(), any(), eq(Sort.Direction.DESC)))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null,
                                        Sort.Direction.DESC);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(null, null, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.DESC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

        }

        @Nested
        @DisplayName("Test Combinazioni Multiple essenziali")
        class CombinazioniTests {

                @Test
                @DisplayName("[TC-07] Tutti i parametri valorizzati")
                void whenAllParametersProvided_thenAllUsed() {
                        when(issueService.getAllIssues(IssueType.BUG, IssueStatus.IN_PROGRESS, IssuePriority.HIGH,
                                        IssueSortField.PRIORITA, Sort.Direction.DESC))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(any())).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(IssueType.BUG, IssueStatus.IN_PROGRESS,
                                        IssuePriority.HIGH,
                                        IssueSortField.PRIORITA, Sort.Direction.DESC);

                        assertThat(result).isNotNull()
                                        .hasSize(1);
                        verify(issueService, times(1)).getAllIssues(IssueType.BUG, IssueStatus.IN_PROGRESS, IssuePriority.HIGH,
                                        IssueSortField.PRIORITA, Sort.Direction.DESC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

                @Test
                @DisplayName("[TC-08] Tutti i parametri NULL -> defaults applicati")
                void whenAllParametersNull_thenDefaultsApplied() {
                        when(issueService.getAllIssues(null, null, null, IssueSortField.TIPOLOGIA, Sort.Direction.ASC))
                                        .thenReturn(Arrays.asList(mockIssue1, mockIssue2));
                        when(issueMapper.toDTO(mockIssue1)).thenReturn(mockDTO1);
                        when(issueMapper.toDTO(mockIssue2)).thenReturn(mockDTO2);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null, null);

                        assertThat(result).isNotNull()
                                        .hasSize(2);
                        verify(issueService, times(1)).getAllIssues(null, null, null, IssueSortField.TIPOLOGIA,
                                        Sort.Direction.ASC);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                        verify(issueMapper, times(1)).toDTO(mockIssue2);
                }
        }

        @Nested
        @DisplayName("Test Stream Mapping")
        class StreamMappingTests {

                @Test
                @DisplayName("[TC-09] Service restituisce lista vuota → mapping corretto")
                void whenServiceReturnsEmptyList_thenReturnsEmptyList() {
                        when(issueService.getAllIssues(any(), any(), any(), any(), any()))
                                        .thenReturn(Collections.emptyList());

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null, null);

                        assertThat(result).isEmpty();
                        verify(issueMapper, never()).toDTO(any());
                }

                @Test
                @DisplayName("[TC-10] Service restituisce 1 elemento → mapping 1 volta")
                void whenServiceReturnsOneElement_thenMapsOnce() {
                        when(issueService.getAllIssues(any(), any(), any(), any(), any()))
                                        .thenReturn(Collections.singletonList(mockIssue1));
                        when(issueMapper.toDTO(mockIssue1)).thenReturn(mockDTO1);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null, null);

                        assertThat(result)
                                        .isNotNull()
                                        .hasSize(1)
                                        .containsExactly(mockDTO1);
                        verify(issueMapper, times(1)).toDTO(mockIssue1);
                }

                @Test
                @DisplayName("[TC-11] Service restituisce N elementi → mapping N volte preservando ordine")
                void whenServiceReturnsMultipleElements_thenMapsAll() {
                        when(issueService.getAllIssues(any(), any(), any(), any(), any()))
                                        .thenReturn(Arrays.asList(mockIssue1, mockIssue2));
                        when(issueMapper.toDTO(mockIssue1)).thenReturn(mockDTO1);
                        when(issueMapper.toDTO(mockIssue2)).thenReturn(mockDTO2);

                        List<IssueDTO> result = issueController.getAllIssues(null, null, null, null, null);

                        assertThat(result)
                                        .isNotNull()
                                        .hasSize(2)
                                        .containsExactly(mockDTO1, mockDTO2);
                        verify(issueMapper, times(2)).toDTO(any());
                }
        }
}
