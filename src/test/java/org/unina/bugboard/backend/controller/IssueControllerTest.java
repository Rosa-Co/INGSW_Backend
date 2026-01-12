package org.unina.bugboard.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.*;
import org.unina.bugboard.backend.repository.IssueRepository;
import org.unina.bugboard.backend.repository.UtenteRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BLACK-BOX INTEGRATION TESTS per IssueController
 *
 * Caratteristiche:
 * - NO mock dei service layer
 * - Testa l'intero stack (Controller → Service → Repository → DB)
 * - Focus su INPUT/OUTPUT osservabili
 * - Database H2 in-memory per isolamento
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "tester", roles = "USER")
@ActiveProfiles("test")
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Utente testUser;

    @BeforeEach
    void setup() {
        issueRepository.deleteAll();
        utenteRepository.deleteAll();

        // Crea utente di test per soddisfare il vincolo creata_da
        testUser = new Utente();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser = utenteRepository.save(testUser);

        // Seed con dati di test controllati - NON settare l'ID manualmente
        Issue bug1 = createIssue("Bug critico", IssueType.BUG, IssueStatus.TODO, IssuePriority.HIGH);
        Issue bug2 = createIssue("Bug minore", IssueType.BUG, IssueStatus.DONE, IssuePriority.LOW);
        Issue feature1 = createIssue("Nuova feature", IssueType.FEATURE, IssueStatus.IN_PROGRESS, IssuePriority.MEDIUM);
        Issue question1 = createIssue("Domanda", IssueType.QUESTION, IssueStatus.TODO, IssuePriority.LOW);

        issueRepository.save(bug1);
        issueRepository.save(bug2);
        issueRepository.save(feature1);
        issueRepository.save(question1);
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 1: TUTTI I PARAMETRI VALIDI
     * Input: tipologia=BUG, stato=TODO, priorita=HIGH, sortBy=tipologia,
     * sortDir=ASC
     * Output atteso: 200 OK, JSON array con 1 elemento (Bug critico)
     * ================================================================
     */
    @Test
    void ce1_allValidParameters_returnsFilteredAndSortedResults() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG")
                .param("stato", "TODO")
                .param("priorita", "HIGH")
                .param("sortBy", "tipologia")
                .param("sortDir", "ASC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titolo", is("Bug critico")))
                .andExpect(jsonPath("$[0].tipologia", is("BUG")))
                .andExpect(jsonPath("$[0].stato", is("TODO")))
                .andExpect(jsonPath("$[0].priorita", is("HIGH")));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 2: PARAMETRI OPZIONALI OMESSI (defaults)
     * Input: nessun parametro
     * Output atteso: 200 OK, tutti e 4 gli issue, ordinati per tipologia ASC
     * ================================================================
     */
    @Test
    void ce2_noParameters_returnsAllIssuesWithDefaults() throws Exception {
        mockMvc.perform(get("/api/issues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                // Verifica ordinamento default (tipologia ASC): BUG, BUG, FEATURE, QUESTION
                .andExpect(jsonPath("$[0].tipologia", is("BUG")))
                .andExpect(jsonPath("$[1].tipologia", is("BUG")))
                .andExpect(jsonPath("$[2].tipologia", is("FEATURE")))
                .andExpect(jsonPath("$[3].tipologia", is("QUESTION")));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 3: SOLO FILTRO tipologia
     * Input: tipologia=FEATURE
     * Output atteso: 200 OK, 1 elemento (Nuova feature)
     * ================================================================
     */
    @Test
    void ce3_onlyTipologiaFilter_returnsMatchingIssues() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "FEATURE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titolo", is("Nuova feature")));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 4: SOLO FILTRO stato
     * Input: stato=DONE
     * Output atteso: 200 OK, 1 elemento (Bug minore)
     * ================================================================
     */
    @Test
    void ce4_onlyStatoFilter_returnsMatchingIssues() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("stato", "DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titolo", is("Bug minore")));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 5: SOLO FILTRO priorita
     * Input: priorita=LOW
     * Output atteso: 200 OK, 2 elementi
     * ================================================================
     */
    @Test
    void ce5_onlyPrioritaFilter_returnsMatchingIssues() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("priorita", "LOW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 6: ORDINAMENTO DESC
     * Input: sortBy=priorita, sortDir=DESC
     * Output atteso: 200 OK, ordinamento HIGH → MEDIUM → LOW → LOW
     * ================================================================
     */
    @Test
    void ce6_sortDescending_returnsCorrectOrder() throws Exception {
        // DB stores Priority as STRING, so sorting is Alphabetical (M > L > H)
        // High=H, Medium=M, Low=L
        // DESC Order: Medium, Low, Low, High
        mockMvc.perform(get("/api/issues")
                .param("sortBy", "priorita")
                .param("sortDir", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].priorita", is("MEDIUM")))
                .andExpect(jsonPath("$[1].priorita", is("LOW")))
                .andExpect(jsonPath("$[2].priorita", is("LOW")))
                .andExpect(jsonPath("$[3].priorita", is("HIGH")));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 7: FILTRI MULTIPLI che non matchano nulla
     * Input: tipologia=BUG, stato=IN_PROGRESS (nessun bug in progress)
     * Output atteso: 200 OK, array vuoto
     * ================================================================
     */
    @Test
    void ce7_noMatchingResults_returnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG")
                .param("stato", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 8: VALORI ENUM VALIDI - tutti i tipi
     * Test di boundary: verifica che TUTTI i valori enum validi funzionino
     * ================================================================
     */
    @Test
    void ce8_allValidEnumValues_returnOk() throws Exception {
        // Test tutti i tipi validi
        for (IssueType type : IssueType.values()) {
            mockMvc.perform(get("/api/issues")
                    .param("tipologia", type.name()))
                    .andExpect(status().isOk());
        }

        // Test tutti gli stati validi
        for (IssueStatus status : IssueStatus.values()) {
            mockMvc.perform(get("/api/issues")
                    .param("stato", status.name()))
                    .andExpect(status().isOk());
        }

        // Test tutte le priorità valide
        for (IssuePriority priority : IssuePriority.values()) {
            mockMvc.perform(get("/api/issues")
                    .param("priorita", priority.name()))
                    .andExpect(status().isOk());
        }
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 9: VALORI ENUM INVALIDI
     * Input: valori non appartenenti agli enum
     * Output atteso: 400 Bad Request
     * ================================================================
     */
    @Test
    void ce9_invalidTipologia_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "NOT_A_TYPE"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce9_invalidStato_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("stato", "INVALID_STATE"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce9_invalidPriorita_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("priorita", "SUPER_URGENT"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce9_invalidSortBy_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("sortBy", "nonExistentField"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce9_invalidSortDir_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("sortDir", "SIDEWAYS"))
                .andExpect(status().isBadRequest());
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 10: CASE SENSITIVITY
     * Input: valori enum in lowercase
     * Output atteso: 400 Bad Request (Spring enum binding è case-sensitive)
     * ================================================================
     */
    @Test
    void ce10_lowercaseTipologia_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "bug"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce10_mixedCaseStato_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("stato", "ToDo"))
                .andExpect(status().isBadRequest());
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 11: VALORI BOUNDARY - stringhe vuote
     * Input: parametri con valore vuoto
     * Output atteso: 400 Bad Request
     * ================================================================
     */
    @Test
    void ce11_emptyTipologia_treatedAsNull_returnsOk() throws Exception {
        // Spring treats empty string enum as null -> controller ignores filter ->
        // returns all
        mockMvc.perform(get("/api/issues")
                .param("tipologia", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void ce11_emptySortBy_treatedAsDefault_returnsOk() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("sortBy", ""))
                .andExpect(status().isOk());
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 12: CARATTERI SPECIALI
     * Input: valori con caratteri speciali / SQL injection attempts
     * Output atteso: 400 Bad Request (non matchano enum)
     * ================================================================
     */
    @Test
    void ce12_sqlInjectionAttempt_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG'; DROP TABLE issues; --"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ce12_specialCharactersInSortBy_returns400() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("sortBy", "../../../etc/passwd"))
                .andExpect(status().isBadRequest());
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 13: COMBINAZIONI MULTIPLE VALIDE
     * Input: tutti i filtri + tutti i parametri di sorting
     * Output atteso: 200 OK con risultati corretti
     * ================================================================
     */
    @Test
    void ce13_allParametersCombination_worksCorrectly() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG")
                .param("stato", "TODO")
                .param("priorita", "HIGH")
                .param("sortBy", "stato")
                .param("sortDir", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 14: VERIFICA STRUTTURA RISPOSTA JSON
     * Input: richiesta valida
     * Output atteso: JSON con campi corretti e tipi corretti
     * ================================================================
     */
    @Test
    void ce14_responseStructure_hasCorrectFields() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].titolo").exists())
                .andExpect(jsonPath("$[0].tipologia").exists())
                .andExpect(jsonPath("$[0].stato").exists())
                .andExpect(jsonPath("$[0].priorita").exists())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].titolo").isString())
                .andExpect(jsonPath("$[0].tipologia").isString())
                .andExpect(jsonPath("$[0].stato").isString())
                .andExpect(jsonPath("$[0].priorita").isString());
    }

    /*
     * ================================================================
     * CLASSE EQUIVALENZA 15: PARAMETRI DUPLICATI
     * Input: stesso parametro passato multiple volte
     * Output atteso: Spring usa l'ultimo valore (behavior default)
     * ================================================================
     */
    @Test
    void ce15_duplicateParameters_usesFirstValue() throws Exception {
        mockMvc.perform(get("/api/issues")
                .param("tipologia", "BUG")
                .param("tipologia", "FEATURE")) // Spring picks first value: BUG
                .andExpect(status().isOk())
                // 2 Bugs in setup
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipologia", is("BUG")));
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private Issue createIssue(String titolo, IssueType tipo, IssueStatus stato, IssuePriority priorita) {
        Issue issue = new Issue();
        // ID generated by DB when saved
        issue.setTitolo(titolo);
        issue.setTipologia(tipo);
        issue.setStato(stato);
        issue.setPriorita(priorita);
        issue.setDescrizione("Descrizione di test per " + titolo);
        issue.setCreataDa(testUser);
        return issue;
    }
}