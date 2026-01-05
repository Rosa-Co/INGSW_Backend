package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Commento;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione dei servizi relativi ai commenti.
 * Fornisce metodi per recuperare e creare commenti.
 */
public interface CommentoService {
    /**
     * Recupera tutti i commenti presenti nel sistema.
     *
     * @return Una lista di tutti i commenti.
     */
    List<Commento> getAllComments();

    /**
     * Recupera tutti i commenti associati a una specifica segnalazione (Issue).
     *
     * @param issueId L'ID della segnalazione per cui recuperare i commenti.
     * @return Una lista di commenti associati all'ID della segnalazione fornito.
     */
    List<Commento> getCommentsByIssueId(Integer issueId);

    /**
     * Recupera un commento specifico basato sul suo ID.
     *
     * @param id L'ID del commento da recuperare.
     * @return Un Optional contenente il commento se trovato, altrimenti vuoto.
     */
    Optional<Commento> getCommentById(Integer id);

    /**
     * Crea un nuovo commento nel sistema.
     *
     * @param commento L'oggetto Commento da creare.
     * @return Il commento creato e salvato.
     */
    Commento createComment(Commento commento);

}
