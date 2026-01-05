package org.unina.bugboard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unina.bugboard.backend.model.Commento;

import java.util.List;

/**
 * Repository JPA per la gestione dell'entità Commento.
 * Fornisce metodi per l'accesso ai dati relativi ai commenti nel database.
 */
public interface CommentoRepository extends JpaRepository<Commento, Integer> {
    /**
     * Recupera una lista di commenti associati a una specifica segnalazione
     * (Issue).
     *
     * @param issueId L'ID della segnalazione.
     * @return Una lista di Commento associati alla segnalazione specificata.
     */
    List<Commento> findByAppartieneId(Integer issueId);
}
