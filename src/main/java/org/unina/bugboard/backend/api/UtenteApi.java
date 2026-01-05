package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;

import java.util.List;

/**
 * Interfaccia API per la gestione degli utenti.
 */
@RequestMapping("/api/users")
public interface UtenteApi {

    /**
     * Recupera tutti gli utenti.
     *
     * @return lista di tutti gli utenti
     */
    @GetMapping
    List<UtenteDTO> getAllUsers();

    /**
     * Recupera un utente per ID.
     *
     * @param id l'ID dell'utente
     * @return l'utente trovato
     */
    @GetMapping("/{id}")
    ResponseEntity<UtenteDTO> getUserById(@PathVariable Integer id);

    /**
     * Recupera un utente per email.
     *
     * @param email l'email dell'utente
     * @return l'utente trovato
     */
    @GetMapping("/email/{email}")
    ResponseEntity<UtenteDTO> getUserByEmail(@PathVariable String email);

    /**
     * Crea un nuovo utente (solo ADMIN).
     *
     * @param userCreationRequest richiesta di creazione utente
     * @return l'utente creato
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UtenteDTO> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest);
}
