package org.unina.bugboard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unina.bugboard.backend.model.Utente;

import java.util.Optional;

/**
 * Repository JPA per la gestione dell'entità Utente.
 * Fornisce metodi per l'accesso ai dati degli utenti nel database.
 */
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    /**
     * Recupera un utente dato il suo indirizzo email.
     *
     * @param email L'indirizzo email dell'utente da cercare.
     * @return Un Optional contenente l'Utente se trovato, altrimenti vuoto.
     */
    Optional<Utente> findByEmail(String email);
}
