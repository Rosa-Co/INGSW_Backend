package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Utente;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione dei servizi relativi agli utenti.
 * Fornisce metodi per recuperare e creare utenti.
 */
public interface UtenteService {
    /**
     * Recupera tutti gli utenti presenti nel sistema.
     *
     * @return Una lista di tutti gli utenti.
     */
    List<Utente> getAllUsers();

    /**
     * Recupera un utente specifico basato sul suo ID.
     *
     * @param id L'ID dell'utente da recuperare.
     * @return Un Optional contenente l'utente se trovato, altrimenti vuoto.
     */
    Optional<Utente> getUserById(Integer id);

    /**
     * Recupera un utente specifico basato sulla sua email.
     *
     * @param email L'email dell'utente da recuperare.
     * @return Un Optional contenente l'utente se trovato, altrimenti vuoto.
     */
    Optional<Utente> getUserByEmail(String email);

    /**
     * Crea un nuovo utente nel sistema.
     *
     * @param utente L'oggetto Utente da creare.
     * @return L'utente creato e salvato.
     */
    Utente createUser(Utente utente);

}
