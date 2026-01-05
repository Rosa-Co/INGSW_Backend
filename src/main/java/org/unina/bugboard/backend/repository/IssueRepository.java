package org.unina.bugboard.backend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

import java.util.List;

/**
 * Repository JPA per la gestione dell'entità Issue (Segnalazione).
 * Fornisce metodi per l'accesso ai dati, filtraggio e ricerca delle
 * segnalazioni.
 */
public interface IssueRepository extends JpaRepository<Issue, Integer> {
        /**
         * Recupera una lista di segnalazioni filtrate per stato.
         *
         * @param stato Lo stato delle segnalazioni da cercare.
         * @return Una lista di Issue che corrispondono allo stato fornito.
         */
        List<Issue> findByStato(String stato);

        /**
         * Recupera una lista di segnalazioni filtrate per priorità.
         *
         * @param priorita La priorità delle segnalazioni da cercare.
         * @return Una lista di Issue che corrispondono alla priorità fornita.
         */
        List<Issue> findByPriorita(String priorita);

        /**
         * Recupera una lista di segnalazioni create da un utente specifico.
         *
         * @param userId L'ID dell'utente creatore.
         * @return Una lista di Issue create dall'utente specificato.
         */
        List<Issue> findByCreataDaId(Integer userId);

        /**
         * Esegue una ricerca complessa di segnalazioni applicando filtri opzionali.
         * I parametri null vengono ignorati nel filtro.
         *
         * @param tipologia Il tipo di segnalazione (opzionale).
         * @param stato     Lo stato della segnalazione (opzionale).
         * @param priorita  La priorità della segnalazione (opzionale).
         * @param sort      Parametri di ordinamento.
         * @return Una lista di Issue che soddisfano i criteri di filtro.
         */
        @Query("SELECT i FROM Issue i WHERE " +
                        "(:tipologia IS NULL OR i.tipologia = :tipologia) AND " +
                        "(:stato IS NULL OR i.stato = :stato) AND " +
                        "(:priorita IS NULL OR i.priorita = :priorita)")
        List<Issue> findIssuesWithFilters(@Param("tipologia") IssueType tipologia,
                        @Param("stato") IssueStatus stato,
                        @Param("priorita") IssuePriority priorita,
                        Sort sort);
}
