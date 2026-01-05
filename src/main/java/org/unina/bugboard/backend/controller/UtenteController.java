package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unina.bugboard.backend.api.UtenteApi;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.mapper.UtenteMapper;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;

/**
 * Controller per la gestione degli utenti.
 * Fornisce endpoint per recuperare, creare e gestire gli utenti.
 */
@RestController
@RequestMapping("/api/users")
public class UtenteController implements UtenteApi {

    private final UtenteService utenteService;
    private final UtenteMapper utenteMapper;

    /**
     * Costruttore per l'iniezione delle dipendenze.
     *
     * @param utenteService servizio per la logica di business degli utenti
     * @param utenteMapper  mapper per convertire tra entity e DTO
     */
    @Autowired
    public UtenteController(UtenteService utenteService,
            UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.utenteMapper = utenteMapper;
    }

    /**
     * Recupera la lista di tutti gli utenti registrati.
     *
     * @return una lista di UtenteDTO contenente tutti gli utenti
     */
    @Override
    public List<UtenteDTO> getAllUsers() {
        return utenteService.getAllUsers().stream()
                .map(utenteMapper::toDTO)
                .toList();
    }

    /**
     * Recupera un utente tramite il suo ID.
     *
     * @param id l'ID dell'utente da cercare
     * @return 200 OK con l'UtenteDTO se trovato, altrimenti 404 Not Found
     */
    @Override
    public ResponseEntity<UtenteDTO> getUserById(Integer id) {
        return utenteService.getUserById(id)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Recupera un utente tramite la sua email.
     *
     * @param email l'email dell'utente da cercare
     * @return 200 OK con l'UtenteDTO se trovato, altrimenti 404 Not Found
     */
    @Override
    public ResponseEntity<UtenteDTO> getUserByEmail(String email) {
        return utenteService.getUserByEmail(email)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuovo utente nel sistema.
     *
     * @param userCreationRequest richiesta contenente i dati del nuovo utente
     * @return 200 OK con l'UtenteDTO dell'utente appena creato
     */
    @Override
    public ResponseEntity<UtenteDTO> createUser(UserCreationRequest userCreationRequest) {
        Utente utenteToCreate = utenteMapper.toEntity(userCreationRequest);
        Utente created = utenteService.createUser(utenteToCreate);
        return ResponseEntity.ok(utenteMapper.toDTO(created));
    }
}
