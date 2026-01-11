package org.unina.bugboard.backend.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueSortField;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione dei servizi relativi alle segnalazioni (Issue).
 * Fornisce metodi per recuperare, creare, filtrare segnalazioni e gestire
 * upload di immagini.
 */
public interface IssueService {
        /**
         * Recupera tutte le segnalazioni presenti nel sistema.
         *
         * @return Una lista di tutte le segnalazioni.
         */
        List<Issue> getAllIssues();

        /**
         * Recupera una segnalazione specifica basata sul suo ID.
         *
         * @param id L'ID della segnalazione da recuperare.
         * @return Un Optional contenente la segnalazione se trovata, altrimenti vuoto.
         */
        Optional<Issue> getIssueById(Integer id);

        /**
         * Crea una nuova segnalazione nel sistema.
         *
         * @param issue L'oggetto Issue da creare.
         * @return La segnalazione creata e salvata.
         */
        Issue createIssue(Issue issue);

        /**
         * Recupera le segnalazioni filtrate per stato.
         *
         * @param status Lo stato delle segnalazioni da recuperare.
         * @return Una lista di segnalazioni che corrispondono allo stato fornito.
         */
        List<Issue> getIssuesByStatus(String status);

        /**
         * Recupera tutte le segnalazioni applicando filtri opzionali e ordinamento.
         *
         * @param tipologia Il tipo di segnalazione per il filtraggio (opzionale).
         * @param stato     Lo stato della segnalazione per il filtraggio (opzionale).
         * @param priorita  La priorità della segnalazione per il filtraggio
         *                  (opzionale).
         * @param sortBy    Il campo per cui ordinare i risultati.
         * @param sortDir   La direzione dell'ordinamento (es. "asc", "desc").
         * @return Una lista di segnalazioni filtrate e ordinate.
         */
        List<Issue> getAllIssues(IssueType tipologia, IssueStatus stato, IssuePriority priorita, IssueSortField sortBy,
                                 Sort.Direction sortDir);

        /**
         * Carica un'immagine associata a una segnalazione.
         *
         * @param issueId L'ID della segnalazione a cui associare l'immagine.
         * @param file    Il file dell'immagine da caricare.
         * @return Il percorso relativo dell'immagine salvata.
         * @throws java.io.IOException Se si verifica un errore durante il salvataggio
         *                             del file.
         */
        String uploadImage(Integer issueId, MultipartFile file)
                        throws java.io.IOException;

        /**
         * Carica una risorsa immagine dal file system.
         *
         * @param filename Il nome del file dell'immagine da caricare.
         * @return La risorsa dell'immagine.
         * @throws java.io.IOException Se il file non viene trovato o si verifica un
         *                             errore di lettura.
         */
        Resource loadImage(String filename) throws java.io.IOException;
}
